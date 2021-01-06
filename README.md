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
Installers will be available on the main Populus page, https://cbs.umn.edu/populus/.

To build and run from source, use the gradle wrapper in the top directory:

`$ ./gradlew bootRun`

If you are using JDK 14 or later, you can also create installer/packager for the
operating system type you are running on:

`$ ./gradlew jpackage`

The images and installers will be in the `build` directory.

Note that for Windows, you'll need to run `gradlew.bat` instead, and need to install [WiX](https://wixtoolset.org) to package.

# Feedback
If you find bugs, irregularities, places for improvement, or have other comments, please email populus@umn.edu.

# Language Support
Spanish translations of some of the more basic models are provided. We would be interested in corresponding with people who would be able to help with other translations.  If interested, please email populus@umn.edu.

# Programming Credits 
Java versions: Amos Anderson, Lars Roe, Sharareh Noorbaloochi \
DOS versions: Chris Bratteli

# License
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

