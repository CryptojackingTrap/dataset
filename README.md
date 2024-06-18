# cryptojackingtrap-dataset
This project involves code for generating or processing datasets needed for CryptojackingTrap evaluation. 

The raw data can be accessed via the following link on IEEE Data Port:
https://ieee-dataport.org/documents/cryptojackingtrap



# Network Note:

note that if you are connected to a public network such as university, because of security limitation policies setting up, the miner can not connect to the network and unexpectedly failed with not straightforward error message. So Don’t use public networks for this purpose. 

# Antivirus Note:

note that by extracting the miners compressed files, some of their files are detected by your installed antivirus and then restore those files before testing.


# Sandbox setup notes:
Setup hints:
first of all we create a VM with above mentioned settings and wile it is shut down we apply Number1 changes and then restart it and then start to install the win11. 

**1-** To solve “he PC doesn’t meet the minimum system requirements to install Windows 11” regardless of existing minimum requirements of win11 (https://www.microsoft.com/en-us/windows/windows-11-specifications?r=1), we added following line in the .vmx file located in VMware “Working directory”:

managedVM.autoAddVTPM = "software"

**2-** in the selecting the country, you may face “oobekeyboard” error, then if so turn of the internet connection just inside the VMware and select “Try again” and at times simply selecting “Try again” numerous times will solve the problem. then for the next step turn (keyboard layout) turn the network connection on again. 

VM is made ready with Cryptojackingtrap-Monotor and appropreate dll file.
