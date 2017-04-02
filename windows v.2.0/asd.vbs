strComputer = "." '(DESKTOP-0NFOGEC)
Set wmi = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set wmiEvent = wmi.ExecNotificationQuery("select * from __InstanceOperationEvent within 1 where TargetInstance ISA 'Win32_PnPEntity' and TargetInstance.Description='USB Mass Storage Device'") 
While True
Set usb = wmiEvent.NextEvent()

strPassword = "@lph@"
Select Case usb.Path_.Class
Case "__InstanceCreationEvent" WScript.Echo("USB device found")
sPassword = InputBox("Enter your password")

If sPassword = strPassword Then
Dim objShell
Set objShell = WScript.CreateObject( "WScript.Shell" )
objShell.run"G:\compileit.bat"
Set objShell = Nothing
Else
Dim outShell
Set outShell = WScript.CreateObject( "WScript.Shell" )
outShell.run"G:\lockit.bat"
Set outShell = Nothing
End If

Case "__InstanceDeletionEvent" WScript.Echo("USB device removed")
Case "__InstanceModificationEvent" WScript.Echo("USB device modified")
End Select
Wend
