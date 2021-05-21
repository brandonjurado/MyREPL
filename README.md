# MyREPL 💻
Homemade REPL shell command line app that drives a simple in-memory key/value storage system. This system allows for nested transactions. A transaction can then be committed or aborted.

## Getting Started
### Prerequisites

* OpenJDK 16
### Usage
Example Run:
```
$ MyREPL
> WRITE a hello
> READ a
hello
> START
> WRITE a hello-again
> READ a
hello-again
> START
> DELETE a
> READ a
Key not found: a
> WRITE a once-more
> READ a
once-more
> ABORT
> READ a
hello
> QUIT
Exiting...
```
