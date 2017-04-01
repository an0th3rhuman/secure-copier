strComputer = "." '(DESKTOP-0NFOGEC)
Set wmi = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set wmiEvent = wmi.ExecNotificationQuery("select * from __InstanceOperationEvent within 1 where TargetInstance ISA 'Win32_PnPEntity' and TargetInstance.Description='USB Mass Storage Device'") 
While True
Set usb = wmiEvent.NextEvent()
Select Case usb.Path_.Class
Case "__InstanceCreationEvent" WScript.Echo("USB device found")
Dim objShell
Set objShell = WScript.CreateObject( "WScript.Shell" )
objShell.Run("""G:\compileit.bat""")
Set objShell = Nothing
Case "__InstanceDeletionEvent" WScript.Echo("USB device removed")
Case "__InstanceModificationEvent" WScript.Echo("USB device modified")
End Select
Wend
