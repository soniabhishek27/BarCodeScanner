
//THIS IS A JAVASCRIPT CODE WRITTEN IN GOOGLE APP SCRIPT
// THIS IS A SERVER CREATED AT GOOGLE SHEETS ALL THE DATA WILL BE UPLAODED IN THE GOOGLE SHEETS


function doGet(e)
{
  var ss = SpreadsheetApp.openByUrl("ENTER YOUR GOOGLE SHEETS URL");
  var sheet = ss.getSheetByName("Sheet1");
  insert(e,sheet);
   
}

function doPost(e)
{
   var ss = SpreadsheetApp.openByUrl("ENTER YOUR GOOGLE SHEETS URL");
   var sheet = ss.getSheetByName("Sheet1");
   return insert(e,sheet);
  
}

function insert(e,sheet) {
  
  var scannedData= e.parameter.sdata;
  var d = new Date();
  var ctime = d.toLocaleString();
  
  sheet.appendRow([scannedData,ctime]);
  
  
}
