package jenkins_workflow

node() {
    stage concurrency: 1, name: 'COMMIT'
    checkOut()
    clean()
    buildWithoutAcceptanceTests()
    collectTestResults()

    stage concurrency: 1, name: 'TEST'
    resetDb('dev')
    buildWar('dev')
    moveWarIntoTomcatHome('dev')
    restartTomcat('dev')
    buildWithAcceptanceTests('dev')
    collectTestResults()
}

stage 'AFTER TEST'
input 'Do you want to deploy to ACC? '

node() {
    stage concurrency: 1, name: 'ACCEPTANCE'
    checkOutTag()
    //resetDb('acc')
    //buildWar('acc')
    //moveWarIntoTomcatHome('acc')
    //restartTomcat('acc')
}

stage 'AFTER ACC'
input 'Do you want to publish this version to Bilbiothouris?'

node() {
    stage concurrency: 1, name: 'PUBLISH'
    mergeTagToMaster()
}

//----------------------------------------------------------------

def checkOut() {
    //clearWorkspace()
    //sh 'mkdir bibliothouris'
    dir('bibliothouris') {
        git branch: 'dev', url: ' https://github.com/cegeka/bibliothouris-2015.git'
    }
}

def clearWorkspace() {
    sh 'rm -rf *'
}

def clean() {
    def mvnHome = tool 'M3'
    dir('bibliothouris') {
        sh "${mvnHome}/bin/mvn clean -Dspring.profiles.active=dev -Pdev"
    }
}

def buildWithoutAcceptanceTests() {
    def mvnHome = tool 'M3'
    dir('bibliothouris') {
        //sh "${mvnHome}/bin/mvn test -Dspring.profiles.active=dev -Pdev"
        sh "${mvnHome}/bin/mvn verify -Dit.test=\"integration/**\" -Dspring.profiles.active=dev -Pdev"
    }
}

def collectTestResults() {
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
    verifyTests()
}

private void verifyTests() {
    println 'Current build status: ' + currentBuild.result
    if (currentBuild.result != null && !currentBuild.result.equalsIgnoreCase("STABLE")) {
        error 'tests failed! Stop the pipeline and fix the build!!!'
    }
}

def resetDb(profile) {
    def mvnHome = tool 'M3'
    dir('bibliothouris') {
        sh "${mvnHome}/bin/mvn flyway:clean flyway:migrate -P" + profile
    }
}

def buildWar(profile){
    def mvnHome = tool 'M3'
    dir('bibliothouris') {
        sh "${mvnHome}/bin/mvn war:war -P" + profile + " -Dspring.profiles.active=" + profile
    }
}

def moveWarIntoTomcatHome(profile){
    def tomcat = ""
    if (profile == "dev")
        tomcat = "7.0.67"
    else if (profile == "acc")
        tomcat = "8.0.28"

    sh 'sudo rm -rf /opt/apache-tomcat-' + tomcat + '/webapps/ROOT'
    sh 'sudo rm -f /opt/apache-tomcat-' + tomcat + '/webapps/ROOT.war'
    sh "sudo mv /var/lib/jenkins/jobs/bibliothouris/workspace/bibliothouris/target/bibliothouris.war /opt/apache-tomcat-" + tomcat + "/webapps"
    sh "sudo mv /opt/apache-tomcat-" + tomcat + "/webapps/bibliothouris.war /opt/apache-tomcat-" + tomcat + "/webapps/ROOT.war"
}

def restartTomcat(profile){
    if (profile == "dev") {
        sh "sudo sh /opt/apache-tomcat-7.0.67/bin/shutdown.sh"
        sh "sudo sh /opt/apache-tomcat-7.0.67/bin/startup.sh"
    } else if (profile == "acc") {
        sh "sudo sh /opt/apache-tomcat-8.0.28/bin/shutdown.sh"
        sh "sudo sh /opt/apache-tomcat-8.0.28/bin/startup.sh"
    }
}

def buildWithAcceptanceTests(profile) {
    def mvnHome = tool 'M3'
    dir('bibliothouris') {
        sh "${mvnHome}/bin/mvn clean verify -DskipTests -Dit.test=\"acceptance/**\" -Dspring.profiles.active=" + profile + " -P" + profile
    }
}

def checkOutTag() {
    //clearWorkspace()
    sh 'rm -rf bibliothouris-2015'	// se va scoate
    sh 'git clone --branch dev git@github.com:cegeka/bibliothouris-2015.git'
}

def migrateDb(profile) {
    def mvnHome = tool 'M3'
    dir('bibliothouris-2015') {
        sh "${mvnHome}/bin/mvn flyway:migrate -P" + profile
    }
}

def mergeTagToMaster() {
    checkOutMaster()
    dir('bibliothouris-2015') {
        sh 'git config --global user.name "valentinabojan"'
        sh 'git config --global push.default matching'
        sh 'git fetch --tags origin'
        sh 'git merge origin/dev'
        sh 'git push'
    }
}

def checkOutMaster() {
    //clearWorkspace()
    sh 'rm -rf bibliothouris-2015'	// se va scoate
    sh 'git clone --branch master https://github.com/cegeka/bibliothouris-2015.git'
}