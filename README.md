## Main technologies and tools:
- Java
- Spring Framework(Boot, Data, Security)
- Hibernate
- PostgreSQL
- JUnit

## Overview
*(Still in progress)* Backend of an application for learning through flashcards. The application allows you to create (end edit) your own sets and add flashcards to them. The way of learning is based on [Leitner's system](https://en.wikipedia.org/wiki/Leitner_system), in this case the flashcards are divided into 5 levels:
- **1 level** *(repetition every day)*
- **2 level** *(repetition every 2 days)*
- **3 level** *(repetition every 4 days)*
- **4 level** *(repetition every week)*
- **5 level** *(repetition every 2 weeks)*

If the user's answer is wrong, the flashcard returns to level one regardless of which level it was on. 

Each set can be set as public or private, if the set is public any logged-in user can view it, in the case of a private set only the owner has access to it.

The security of the application is provided by JWT.
