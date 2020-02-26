# cld-search-engine

This Java project parses a logical expression with words as atoms to create
a search query for a given set of files and in prints the files which satisfy the
expression. It also prints the td-idf of every word in the expression.

## Getting Started

These instructions will 

### Prerequisites

The only prerequisite is the Java Developement Kit (JDK), which is installed
automatically by the build script if you're running Ubuntu by running:

```
apt install jdk
```

And, of course, you need to install git to clone the repository:
```
apt install git
```

### Installing

First clone the repository:
```
git clonehttps://github.com/tnsio/cld-search-engine

```
Then cd into it and run the build script:

```
cd cld-search-engine
./build.sh
```

### Running the application

Give a list of the files you wish to query as arguments
then enter expressions to be evaluated. You can stop by entering 
an empty line.

```
./run.sh file1 file2 file3
cat && !dog
No matches found!


```

You can run the application on some test data by passing the argument `-t`
```
./run.sh -t
brilliant
Following matches found:
./documente_problema/doc02.txt

~~~~~ Td-idf table ~~~~~
Document: ./documente_problema/doc02.txt
brilliant 2.995732273553991


```
