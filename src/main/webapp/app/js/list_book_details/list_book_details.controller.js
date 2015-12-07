(function() {
    angular
        .module("Bibliothouris")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(restService, $routeParams) {
        var vm = this;

        vm.book = {};

        activate();

        function activate() {
            restService
                .getBookDetails($routeParams.bookId)
                .then(function(data){
                    vm.book = data;
                    vm.book.description = "Harry Potter and the Chamber of Secrets is the second novel in the Harry Potter series, written by J. K. Rowling. The plot follows Harry's second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school''s corridors warn that the 'Chamber of Secrets' has been opened and that the 'eir of Slytherin' would kill all pupils who do not come from all-magical families. These threats are followed by attacks which leave residents of the school 'petrified' (frozen like stone). Throughout the year, Harry and his friends Ron Weasley and Hermione Granger investigate the attacks. The book was published in the United Kingdom on 2 July 1998 by Bloomsbury and in the United States on 2 June 1999 by Scholastic Inc. Although Rowling found it difficult to finish the book, it won high praise and awards from critics, young readers and the book industry, although some critics thought the story was perhaps too frightening for younger children. Much like with other novels in the series, Harry Potter and the Chamber of Secrets triggered religious debates; some religious authorities have condemned its use of magical themes, while others have praised its emphasis on self-sacrifice and on the way in which a person''s character is the result of the person''s choices. Several commentators have noted that personal identity is a strong theme in the book, and that it addresses issues of racism through the treatment of non-magical, non-human and non-living characters. Some commentators regard the diary as a warning against uncritical acceptance of information from sources whose motives and reliability cannot be checked. Institutional authority is portrayed as self-serving and incompetent. The book is also known to have some connections to the sixth novel of the series, Harry Potter and the Half-Blood Prince. The film adaptation of the novel, released in 2002, became (at that time) the seventh highest grossing film ever and received generally favourable reviews. Video games loosely based on Harry Potter and the Chamber of Secrets were also released for several platforms, and most obtained favourable reviews.";
                    if (vm.book.cover == null)
                        vm.book.cover = "../icons/default_book.png";
                });
        }
    }
})();