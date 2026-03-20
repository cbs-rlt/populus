[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

This is the official University of Minnesota Populus Git Repository.

# About
Populus is a package of educational software allowing students to manipulate ecological and evolutionary models, producing graphical representations of their dynamics.  It also contains an integrated help system discussing each of the models.
Please go to https://cbs.umn.edu/populus/ for more information about Populus.

# Copyright
Don Alstad \
Department of Ecology, Evolution & Behavior \
University of Minnesota \
1987 Upper Buford Circle \
St. Paul, MN 55108-6097

# How to run
Java or a JavaJDK must be installed. We suggest [OpenJDK](https://openjdk.org), but you can also use [Oracle Java](https://www.java.com/en/download/manual.jsp) (pay close attention to licensing requirements!)

## Version 5.4
### Pure Java
1. Download the appropriate .jar file from [releases](https://github.com/cbs-rlt/populus/releases)
2. Rename the .jar file to `PopInstall.5.4.jar`
#### JRE 1.1.x
1. Open a command prompt and navigate to the download directory
2. Run `jre -cp .:PopInstall.5.4.jar;<path to swingall.jar> load`
#### JRE 1.2 or later
If your system has been configured to automatically launch .jar files with Java, then double-click on PopInstall.5.4.jar. Otherwise:
1. Open a command prompt and navigate to the download directory
2. Run `java -jar PopInstall.5.4.jar`

#### Mac with JRE 1.3 or later
1. Make sure Apple's MRJ SDK is installed which includes the JBindery and other tools
2. Launch the JBindery and add then enter load manually in the Command panel.
3. In the Command panel, choose paths for console output and input.
4. In the Classpath panel, enter PopInstall.5.4.jar in the search paths. If you're using JRE 1.1.x, then also include swingall.jar.
5. In the Appearance panel, select any desired appearance features.
6. In the Security panel, choose whether you want to use the code verifier.
7. In any of the panels, select the Run button (or press the Return key) to execute the Java application.

## From Source
### Notes for Windows
- Windows requires [WiX](https://wixtoolset.org) to package
- Windows uses `gradlew.bat` instead of `gradlew`

Use the gradle wrapper, `./gradlew bootRun`, in the top directory to run Populus.

If you are using JDK 14 or later, you can use `./gradlew jpackage` create installer/packager for the operating system type you are running.

The images and installers will be in the `build` directory.

# Feedback
If you find bugs, irregularities, places for improvement, or have other comments, please email populus@umn.edu.

# Language Support
Spanish translations of some of the more basic models are provided. We would be interested in corresponding with people who would be able to help with other translations.  If interested, please email populus@umn.edu.

# Programming Credits 
Java versions: Amos Anderson, Lars Roe, Sharareh Noorbaloochi \
DOS versions: Chris Bratteli

# Terms of Use
Populus software is produced by the University of Minnesota to foster instruction in population biology.  IBM, the University of Minnesota, and the National Science Foundation have underwritten development, and the software is not a commercial venture. Faculty and students at your educational institution may download Populus and use it gratis for non-profit instructional purposes.  You may incorporate links to this site, in nonprofit materials distributed within or beyond your institution.  This is the only venue maintained to provide the current public version of Populus, and you should distribute the link rather than redistributing the software. By making a Populus download, you signify acceptance of these terms.

# License Agreement
© Regents of the University of Minnesota.

This software is released under [GNU General Public License 2.0](LICENSE)

No guarantee or warranty is expressed or implied regarding the quality, performance, or usefulness of the Populus software.  It is distributed free and AS IS; by downloading this software, you acknowledge University of Minnesota as the owner of intellectual property rights for the software and documentation.  The mathematical models themselves are intellectual property of the authors and journals that published them.  Those publications are cited in the help files associated with each module, and the help text itself often paraphrases the original publication closely. The University of Minnesota acknowledges this intellectual debt, and thanks the many authors who have corresponded and collaborated in perfecting the Populus implementation of their work.  All rights not expressly granted with respect to this software are withheld by University of Minnesota. Neither the University of Minnesota, nor any other collaborator involved in the project can be held liable for injury or damages relating to this software.
