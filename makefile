ToDo.class:
	javac -Xlint  *.java
run:
	java -Xmx64m ToDo
clean:
	rm -f *.class
tar:
	tar -cvz makefile *.java -f u17016143.tar.gz