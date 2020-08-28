# TDDirectDeployTool

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2f215251539c4cc8bc18823a49845f3b)](https://app.codacy.com/manual/toby_mcdonald/TDDirectDeployTool?utm_source=github.com&utm_medium=referral&utm_content=tfinnm/TDDirectDeployTool&utm_campaign=Badge_Grade_Settings)

CI tool for automatically deploying code to a server using SSH and SFTP

STOP! this project is not open source and has not been licensed to you, you may not use it! A license will be added to this repo in the near future at which time you will be able to use this tool, in the mean time, please be patient and respect copyright law.
This notice does not apply if you have received a license from me from elsewhere.

## installation
- have java installed
- place the jar in the startup script
- done
(note: the application works best in desktop enviroments which have a system tray, but should work anywhere java is supported)

## usage
Code/files to be deployed should be placed in the this application's "push" folder (located in the same directory as the jar), if this folder has sub directories, it should be placed in the directory with the correct config for the project being deployed.

## configuration
the config files are stored in the "push" folder and end in .TDDDTConfig
this application includes a config wizzard by creating them by hand is recommended
they are structured as follows:
line 1: regex: files being deployed will only follow this config's settings if its file name matches this regex. note that if multiple config's regexs match then all of the matching configs will be deployed to seperately.
line 2: host: this is the host name or IP adress of the remote server being deployed to, it should be open on port 22.
line 3: username: this is the server's ssh username
line 4: password: this is the server's ssh password, this is potentially a vulnerability if the system you are deploying from can be accessed from outside the network
line 5: directory: this is the remote directory that the deployed files will be saved to
line 6: use sudo: this should be true or false and indicates whether or not to prefix the pre-/post-deployment commands with sudo
line 7: sudo password: this is the super user password for if use sudo is true. if use sudo is false this line may be false but must be present
line 8: pre-deploy command: this is the command that will be run before the files are deployed. (note this runs after the pre-deploy temp file is uploaded)
line 9: pre-deploy temp file: this is the URL of the file uploaded before the files are deployed (note this is removed from the remote server after the pre-deploy command is run and before the files are deployed)
line 10: post-deploy command: same as pre-deploy but after the files have been deployed.
line 11: post-deploy temp file: same as pre-deploy but after the files have been deployed.
