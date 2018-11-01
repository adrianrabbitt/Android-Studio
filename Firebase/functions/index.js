const functions = require('firebase-functions');

//import admin module
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// This registration token comes from the client FCM SDKs.
var registrationToken = 'ckpp7jbS42g:APA91bGc0UnHcrpJ-Ao1wHr1bLkuHA4aWNV6kt_9aXquf7TTDb165MaIXuWayel30MPQhVTYElDCot-bsdkHH0TpyVwOGUUpRBjAEJP8LTdLcgWdsJm-DfDr66oqpVUb_29rFPBkohgl';

// See documentation on defining a message payload.
exports.sendNotification = functions.database.ref('/Notifications')
    .onWrite(( change,context)=> {

    // Grab the current value of what was written to the Realtime Database.

   admin.database().ref('/data/7/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
  
  
  
  const request = change.after.val();
  // console.log(request.token);
    var payload = {
        data: {
            title: request.messageUser,
            author: request.messageText
        }
    };

    // Send a message to devices subscribed to the provided topic.

     admin.messaging().sendToDevice(registrationToken, payload);
    })
	
	
	exports.update = functions.database.ref('/Users/{uid}/Info')
    .onWrite(( change,context)=> {

    // Grab the current value of what was written to the Realtime Database.

	const request = change.after.val();
	var Plumber = 'Plumber';
	var Gardening = 'Gardening';
	var Bartender = 'Bartender';
	var Farming = 'Farming'
	var Painting = 'Painting';
	var Electrician = 'Electrician';
	var Carpenter = 'Carpenter';
	var PetCarer = 'Pet Carer';
   console.log(request.job);

	
	
	
   if(request.job === Plumber)
   {
	   

   admin.database().ref('/data/1/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
   }
    if(request.job === Electrician)
   {
	   

   admin.database().ref('/data/2/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
   }
    if(request.job === Gardening)
   {
	   

   admin.database().ref('/data/4/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
   }
  
  
    if(request.job === Farming)
   {
	   

   admin.database().ref('/data/3/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
   }
   
      if(request.job === Painting)
   {
	   

   admin.database().ref('/data/5/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
   }
      if(request.job === Bartender)
   {
	   

   admin.database().ref('/data/6/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
   }
   
      if(request.job === PetCarer)
   {
	   

   admin.database().ref('/data/7/value')
    .transaction(value => {
       
           return (value || 0) + 1;
        
    })
   }
  
  
 

    // Send a message to devices subscribed to the provided topic.


    })
	
	
	
	
	
	
	
	
	
	