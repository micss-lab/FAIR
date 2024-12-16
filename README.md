# FAIR
**FAIR Framework**

* Eclipse version: eclipse-java-2020-03-R-win32
* Jason   version: 2.6.3
* Java    version: SE 11/7

Download Links:

* Jason: https://sourceforge.net/projects/jason/files/jason/version2.6/jason-2.6.3.zip/download
* Eclipse: https://www.eclipse.org/downloads/packages/release/2020-03/r
* Java: https://www.oracle.com/be/java/technologies/javase/jdk11-archive-downloads.html / https://www.oracle.com/be/java/technologies/javase/javase7-archive-downloads.html

Installation Steps:
1. Jason: https://github.com/agentspeakers/jason-er
2. mas2j file allows to define customized agent's by defining an agentClass, for example:

```
MAS turretPlanetAgent
{
	infrastructure: Centralised
	environment: TurretPlanet(1,"Random","Fuzzy")
  agents:
		turretAgent turretAgentFuzzy agentClass FAIR;
  aslSourcePath:
		"src/asl";
}
```
3. Place FAIR.java inside src/java folder.
4. This is already configured for the provided case studies.


**Directories**

1- CaseStudies -> The pages and case study files for each case study for reproducibility.
2- dataInputs  -> Data inputs prepared for each case study to compare boolean and fuzzy BDI approaches.
3- dataOutputs -> Gathered data from the case studies.
4- Experiments -> Experimental Analyses of the output data to compare boolean and fuzzy BDI methods.
5- FAIR.java   -> The java file to use FAIR fuzzy architectural extension and enhancement on BDI agents.
