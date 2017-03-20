import os, hashlib

hash = "678f562f01475d7f7bbdf9c6311d11df333d9448b08b397df0bffe631c0dd007"

while(1):
    passw = os.popen("zenity --password --title='Authentication'").read().strip()
    if hashlib.sha256(passw).hexdigest() == hash:
        break
    else:
        os.popen('zenity --title="Authentication Failure" --error --text="The password you\'ve entered is wrong. Please try again"')

os.popen("zenity --warning --title='Software Limitation' --text='The Software supports only single file copy feature for now. Updates arrive soon! Click OK to continue to the copy wizard'")
cpysrc = os.popen('zenity --file-selection --multiple --title="Select the source location"').read().strip()
 #os.popen("cd "+cpysrc) <-- no need of this as we arge giving absolute path 
    
cpydes = os.popen('zenity --file-selection --directory --title="Select the destination location"').read().strip()
cmd1 = "echo "+passw+" | "+"gpg -c --passphrase-fd 0  -o "+cpydes+"/cryp"+" "+cpysrc
cmd2 = "echo "+passw+" | "+"gpg --passphrase-fd  -o "+cpydes+"/cryp"+" "+cpysrc
if "/Desktop" in cpydes:
    os.popen(cmd1)
if "/Desktop" in cpysrc:
    os.popen(cmd2)
os.popen("zenity --title='Success' --info --text='The requested file has been securely copied successfully!'")

