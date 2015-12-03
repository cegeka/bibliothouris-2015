INSERT INTO BOOK
VALUES
  (BOOK_SEQUENCE.nextVal,
   '978-0-13-235088-4',
   'Clean Code',
   '\tEven bad code can function. But if code isn’t clean, it can bring a development organization
   to its knees. Every year, countless hours and significant resources are lost because of poorly
   written code. But it doesn’t have to be that way. \nThis book is a must for any developer,
   software engineer, project manager, team lead, or systems analyst with an interest in producing
   better code.',
   431,
   '18-JUN-2009',
   'Prentice Hall Pearson Education'
  );
INSERT INTO BOOK
VALUES
  (BOOK_SEQUENCE.nextVal,
   '978-0-321-14653-3',
   'Test-Driven Development',
   '\tQuite simply, test-driven development is meant to eliminate fear in application development.
    While some fear is healthy (often viewed as a conscience that tells programmers to "be careful!"),
    the author believes that byproducts of fear include tentative, grumpy, and uncommunicative
    programmers who are unable to absorb constructive criticism. \nWhen programming teams buy into TDD,
    they immediately see positive results.',
    220,
   '21-DEC-2003',
   'Addison-Wesley'
  );
INSERT INTO BOOK
VALUES
  (BOOK_SEQUENCE.nextVal,
   '978-0-201-61622-4',
   'The Pragmatic Programmer',
   '\tWhat others in the trenches say about The Pragmatic Programmer..."The cool thing about
   this book is that it''s great for keeping the programming process fresh. The book helps you
   to continue to grow and clearly comes from people who have been there." --Kent Beck,
   author of Extreme Programming Explained: Embrace Change "I found this book to be a great mix
   of solid advice and wonderful analogies!" --Martin Fowler, author of Refactoring and
   UML Distilled "I would buy a copy, read it twice, then tell all my colleagues to run out and
   grab a copy. \nThis is a book I would never loan because I would worry about it being lost."
   --Kevin Ruland, Management Science, MSG-Logistics "The wisdom and practical experience of
   the authors is obvious.',
   320,
   '15-JUL-1999',
   'Addison-Wesley'
  );
INSERT INTO BOOK
VALUES
  (BOOK_SEQUENCE.nextVal,
   '978-0-201-48567-7',
   'Refactoring',
   '\tRefactoring is about improving the design of existing code. It is the process of changing a software system in such a way that it does not alter the external
      behavior of the code, yet improves its internal structure. With refactoring you can even
      take a bad design and rework it into a good one. \nThis book offers a thorough discussion
      of the principles of refactoring, including where to spot opportunities for refactoring,
      and how to set up the required tests. There is also a catalog of more than 40 proven
      refactorings with details as to when and why to use the refactoring, step by step
      instructions for implementing it, and an example illustrating how it works.
      \nThe book is written using Java as its principle language, but the ideas are applicable
      to any OO language.',
      431,
   '11-FEB-1999',
   'Addison-Wesley'
  );
INSERT INTO BOOK
VALUES
  (BOOK_SEQUENCE.nextVal,
   '978-0-201-63361-0',
   'Design Patterns',
      '\tCapturing a wealth of experience about the design of object-oriented software,
      four top-notch designers present a catalog of simple and succinct solutions
      to commonly occurring design problems. Previously undocumented, these 23 patterns
      allow designers to create more flexible, elegant, and ultimately reusable designs
      without having to rediscover the design solutions themselves.',
      395,
   '11-FEB-1977',
   'Addison-Wesley'
  );
INSERT INTO BOOK
VALUES
  (BOOK_SEQUENCE.nextVal,
   '978-0-764-55831-3',
   'J2EE Development without EJB',
      '\tThe book begins by examining the limits of EJB technology — what it does well and
      not so well. Then the authors guide you through alternatives to EJB that you can use
      to create higher quality applications faster and at lower cost — both agile methods as
      well as new classes of tools that have evolved over the past few years.
      \nThey then dive into the details, showing solutions based on the lightweight framework
      they pioneered on SourceForge — one of the most innovative open source communities.
      They demonstrate how to leverage practical techniques and tools, including the popular
      open source Spring Framework and Hibernate. ',
      552,
      '10-JUL-2004',
      'Wiley Publishing'
  );
INSERT INTO BOOK
VALUES
  (BOOK_SEQUENCE.nextVal,
   '978-0-321-35668-0',
   'Effective Java',
      '\tAre you looking for a deeper understanding of the Java™ programming language
      so that you can write code that is clearer, more correct, more robust, and more reusable?
      Look no further! Effective Java™, Second Edition, brings together seventy-eight indispensable
      programmer’s rules of thumb: working, best-practice solutions for the programming challenges
      you encounter every day. This highly anticipated new edition of the classic,
      Jolt Award-winning work has been thoroughly updated to cover Java SE 5 and Java SE 6
      features introduced since the first edition. \nBloch explores new design patterns and
      language idioms, showing you how to make the most of features ranging from generics to enums,
      annotations to autoboxing. Each chapter in the book consists of several “items” presented
      in the form of a short, standalone essay that provides specific advice, insight into Java
      platform subtleties, and outstanding code examples.',
      368,
      '12-JUN-2008',
      'Addison-Wesley'
  );


INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Robert C.', 'Martin');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Kent', 'Beck');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Andrew', 'Hunt');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'David', 'Thomas');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Martin', 'Fowler');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Erich', 'Gamma');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Richard', 'Helm');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Ralph', 'Johnson');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'John', 'Vlissides');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Rod', 'Johnson');
INSERT INTO AUTHOR VALUES(AUTHOR_SEQUENCE.nextVal, 'Joshua', 'Bloch');

INSERT INTO BOOK_AUTHOR VALUES(1, 1);
INSERT INTO BOOK_AUTHOR VALUES(2, 2);
INSERT INTO BOOK_AUTHOR VALUES(3, 3);
INSERT INTO BOOK_AUTHOR VALUES(3, 4);
INSERT INTO BOOK_AUTHOR VALUES(4, 5);
INSERT INTO BOOK_AUTHOR VALUES(5, 6);
INSERT INTO BOOK_AUTHOR VALUES(5, 7);
INSERT INTO BOOK_AUTHOR VALUES(5, 8);
INSERT INTO BOOK_AUTHOR VALUES(5, 9);
INSERT INTO BOOK_AUTHOR VALUES(6, 10);
INSERT INTO BOOK_AUTHOR VALUES(7, 11);


INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'SCIENCE');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'SCIENCE-FICTION');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'PROGRAMMING');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'TRAVEL');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'ART');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'SERIES');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'HISTORY');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'TRAVEL');
INSERT INTO CATEGORY VALUES(CATEGORY_SEQUENCE.nextVal, 'AGILE');


INSERT INTO BOOK_CATEGORY VALUES(1, 1);
INSERT INTO BOOK_CATEGORY VALUES(1, 9);
INSERT INTO BOOK_CATEGORY VALUES(2, 1);
INSERT INTO BOOK_CATEGORY VALUES(2, 3);
INSERT INTO BOOK_CATEGORY VALUES(3, 3);
INSERT INTO BOOK_CATEGORY VALUES(4, 3);
INSERT INTO BOOK_CATEGORY VALUES(5, 9);
INSERT INTO BOOK_CATEGORY VALUES(5, 3);
INSERT INTO BOOK_CATEGORY VALUES(6, 3);
INSERT INTO BOOK_CATEGORY VALUES(7, 3);