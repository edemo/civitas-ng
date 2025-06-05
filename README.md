This is a rewrite of civitas, the secure voting system.

See https://www.cs.cornell.edu/projects/civitas/ for the original project.

As civitas was written in jif and java 1.5, while jif and poliglot which gave jif support were also written in java 1.5,
I decided to convert the whole stuff to Java, getting rid of the information flow control within the software.

This version uses modern java, maven, significantly refactored and thorougly tested.

The aim is to have all of the original features of civitas, with the following differences:

- No voting method is supported beyond Condorcet. I do not want to contribute to the further degradation of the quality of civil discourse.
- Adding a procedure along the lines of the Debian General Resolution procedure, to fully support a good community decision making procedure.
- Have an actual, real-world useable client, supporting the possible most intuitive and simple ballot layout for Condorcet.
- Full testing coverage, including 100% mutation coverage for all logic involved.

This is a work in progress. If you want and able to contribute, see CODING.md 

