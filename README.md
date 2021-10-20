# Treasury

A command-line tool to securely encrypt passwords with AES-128, storing them in a database.


----
### Installation

Download the jar file from /target/ and place it somewhere in your path. Run it with
`java -jar /path/to/Treasury*.jar [arguments]`.

You can create an alias on UNIX machines to simplify this. I have the following in my .bashrc:
`alias treasury='java -jar /mnt/c/Users/path/to/Treasury/target/Treasury*.jar'`

This allows me to run commands by typing just `treasury [arguments]`.
### Usage

Run `treasury --help` to view all available commands.

----
### Technology
- Built solely in Java
- Picocli Command Line library
- SQLite for the database, JDBC to connect

### Contributors
- Kai T

# Screenshots

![](/screenshots/screenshot.png)
---
