# MyREPL 💻
Homemade REPL (read-eval-print loop) shell command line app that drives a simple in-memory key/value storage system. This system allows for nested transactions. A transaction can then be committed or aborted.

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
hello-again
> QUIT
Exiting...
```
### Commands

* **READ** - Reads and prints, to stdout, the val associated with key. If the value is not present an error
  is printed to stderr.
* **WRITE** - Stores val in key.
* **DELETE** - Removes a key from store. Future READ commands on that key will return an error.
* **START** - Start a transaction.
* **COMMIT** - Commit a transaction. All actions in the current transaction are committed to the
  parent transaction or the root store. If there is no current transaction an error is output to
  stderr
* **ABORT** - Abort a transaction. All actions in the current transaction are discarded.
* **QUIT** - Exit the REPL cleanly. A message to stderr will be output.
