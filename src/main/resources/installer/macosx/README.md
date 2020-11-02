# Files used for OS X installer/packager

picon.icns - Icon for main app image

Info.plist - update for Description, application type, and Copyright

Populus-background.png - Installer background image (Light Mode)

Populus-background-darkAqua.png - Installer background image (Dark Mode)


# Potential configuration files not included:

Populus-background.tiff - for background image in DMG

Populus-volume.icns - for DMG volume icon (e.g. on desktop when mounted)

Runtime-Info.plist - Runtime properties list for libjli.dylib. I can't think of why you would want to modify this.

Populus-post-image.sh - Custom script that runs between image and DMG/PKG installer

Populus-dmg-setup.scpt - DMG setup script

preinstall - runs before application install

postinstall - runs after application install



