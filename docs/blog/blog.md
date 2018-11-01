#Jobder 

**Adrian Rabbitte**

## My First Blog Entry

-Since the creation of my functional specification I have went through nemourous tutorials on the implemention for the implementation of my project. The main aspect 
 I wanted to grasp was firebase, as this was going to be my storage for and system set up for the android application. The courses I followed are from https://firebase.google.com/docs/?gclid=EAIaIQobChMI6KuO_vKK2QIVhLftCh25RwJYEAAYASAAEgIjmPD_BwE
 I started implementing my project on the 20 December 2017, where I started becoming familier with authentication through firebase, this was a massive learning curve as I was dealing with a new server framework 
 that I had no experience with. This authentication uses email and password to authenticate each user.

-Before I started to deal with the storage I wanted to gain knowledge on the use of google maps as a vital implementation
 of my app involved google maps. Here is a link that I followed when implementing the map. 
 
-From this point I placed out a basic structure of how my application would stand using basic buttons to represent the functionalty of the app this was a very simple protype that I will follow through the implementation 
od the app. 

-When storing data required for the application I would be using Firebase Storage and database, for the database storage I would be storing each user's details as an object, where this object would be 
pushed onto the firebase server. Firebase storage will by used to store images for the application.








## My Second Blog Entry


The last few days I have been working with firebase storage and the front end of the application. The main aspects i looked at was both the Employer and Employee Profiles,
as these were the main assests to the users respectively. The Employee,s main xml components consist's of an image views,card views ,edit texts and text views, these are all 
aligned using linear layouts both horozontal and vertical. 

The design was kept simple to display the users information. I had a number of issues when dealing 
with the layout as a number of views were overlapping each other due to their fixed widths and hieghts 
For the employee profile where he/she can update there personal attributes using edittexts I created a custom class to create a 
border and to highlight the fields for the user to enter, I called the file to be displayed 
by each unique edit text. I had a lot of trial and error when creating the design to look as professional as possible.
Card Views were used in both profiles to display important information to the user of Jobder. For the employee 
the card view dusplayed name, age and views.

The most difficult challenge of my past few days was getting an image from the users device and 
store ith according to their unique id.
Getting the image from the users device was straight forward. I used the following link as a guide to achieve the action.
the first step was to create an intent where the type was established to search through all images located in the device,
the action was set to Intent.Action_Pick this will allow selection of an item and return the 
the item from the data that was selected from the URI to the local data.
To recieve the file path to the selected picture file the intent was passed into a startActivityForResult()
The argument's is a "request code" that identifies the request. when the result intent is recieved,
the callback provides the same request code so the app can properly identify the result and determine how to handle it.

Now that I had the Uri to the selected picture now it was time to upload the image into storage according to the users unique 
id. The first step was to create a reference to the current users dectory in the firebase storage database.
 
" StorageReference childRef = storageRef2.child(userId).child("Profile.jpg")"

where the file was then placed into the data base by its reference using.

        "childRef.putFile(filePath) "

I saved the file by the usersID so that it would be taged to the user , where by using setvalue   k
instead of push would eleminate dublicates if the user re uploads their profile picture.

![cat](https://gitlab.computing.dcu.ie/rabbita3/2018-ca400-rabbita3/tree/master/docs/blog/images/storage.png)








## My Third Blog Entry


In the last week i mainly focused on the actual functionality of the application on how I was able to link up the users information and sync
them with corsponding jobs. I have now developed a strong knowledge and experience with firebase on storing objects and linking them up by their corrsponding uids
I structured the data so that it is formed by linking up the users profiles by passing in their uids as a reference(Bundle extras) to each new activity.
I had to transform the data into a number of objects as 1 wasnt statisifying the purpose as a place holder of the users data. I am curently working on the frame work of 
instant messaging also with the use of firebases realtime data base. I have developed a simple ui where the data is being pushed to the data base and read from using an event listner 
using a listview adapter. 



## My Fourth Blog Entry
In the last two weeks i have been working on  Employer and Employee Communication. This is where the Employer sends a message to the Employee. The Employee does not have 
direct access to the initial communication with the Employer. I started of by designing the UI for the chat interface usinf xml. This layout consists of the message area 
using a EditText where ther user can enter their meaage along with a send button. The toolbar consists of the users name along with the date and the users profile picture. 
Although this is a new activity the picture is downloaded according to the users unique Uid using glide where the bitmap is tranformed into a circular image using 
RoundedBitmapDrawable.

protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);

                            circularBitmapDrawable.setCircular(true);
                            image.setImageDrawable(circularBitmapDrawable);
                        }
						
To have the application working so that the messages were instant, i implemented the storage of messages using Firebases Realtime database.
This would aloow that if a child was added it would be notifying the application as a new message was inserted by using an event listner.
Classes implementing this interface can be used to receive events about changes in the child locations of a given DatabaseReference ref.
The listner is attached to a location using addChildEventListener(ChildEventListener) and the appropriate method will be triggered when changes occur.
Altough this sounded as a straight forward task it was far from what I was expecting.
When I had completed the UI for the chat activity I started of by creating my message class that would represent a single segment of a message, where each object
would be represented in a listview. The object would consist of messageText(The actual message itself)  User,(The personal creating the message),sender(the reciever of the 
message) and the time(time and date on which the message object was created.

This object represented the message within the firebase database. The text was pulled from the editText
and inserted into the object where the obect was stored inside the database. The object was pushed into the database 
via the reference of the firebase database.

FirebaseDatabase.getInstance().getReference().child("chats").child(chat_room1)
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(), FirebaseAuth.getInstance()
                                    .getCurrentUser().getEmail())
                            );
							
Inserting the data was straight forward as I was familiar with this task from previous work with database insertion in the project. Now that I had my messages
inserting I had to now pull them from firebase and display them in a listview, for an incremental amount of objects. To achieve this I had to create an MessageAdapter.
Here the message Adapter class extented the actual ArrayAdapter class using the chatMessage object as its value
in each block of the array. 

public class MessageAdapter extends ArrayAdapter<ChatMessage>

To customize what type of view is used for the data object, override getView(int, View, ViewGroup) and inflate a view resource.

 public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ChatMessage message = getItem(position);
       ;

        if( FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().equals(message.getMessageUser())) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.message_other,parent,false);
            TextView text2 = (TextView) convertView.findViewById(R.id.message_text);
            //TextView text3 = (TextView) convertView.findViewById(R.id.message_time);
            //text1.setText(message.getMessageUser());
            text2.setText(message.getMessageText());

        }
		
		return view
		
}
Here the chatMessage object when recieved is assigned to its corsponding xml file 
that will be displaying the message. 
Once I had my ArrayAdapter class up and running I was able to use it for my list view of 
messages.
adapter = new MessageAdapter(this, R.layout.message, chatApplications);

I first created an arraylist of ChatMessage type objects, where I then set my adapter.
 listView.setAdapter(adapter);
 
The objects were added to the adapter once there was a child added within the firebase 
database using the eventListner where the dataSnapshot is asigned to an object and inserted 
into the array.

public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                    adapter.add(message);
                }
				
Now that I had messages inserting and receving, this was only within one pool of messages 
where everyting was treated as a group chat. My next task was to implement the instant messaging as a 
one to one system where each user of the application had endured privacy. 
To acheve this I had to create 2 ChatRooms per 1 connection(Sender,Recever). The chat rooms would represent 
the sender and receviers UID respectivly. Where the recevier and senders uid is concationated.
e.g
    chatroom1 = (Sender Uid + Recevier Uid)
	chatroom2 = (Reciever Uid + Sender Uid) 
	
	  chat_room1 = FirebaseAuth.getInstance().getCurrentUser().getUid() +"_"+newString;
      chat_room2 = newString+"_"+FirebaseAuth.getInstance().getCurrentUser().getUid();

The reason I created 2 was to account for both the senders pool and the recievers pool of messages.
This allowed to create seperate storages of pools of messages for each sender and recevier.

FirebaseDatabase.getInstance().getReference().child("users").child(newString)

## My Fifth Blog Entry
 
When undergoing simple testing I realised that the implementation of my location Listners was not working,
I created an abstract class to store the location where latitude and longitude vairables were pulled from the location.
Using the locationManager I was able to get access to the system location services. The error that I was continiously expercing was with 
Location permissions.


## My Sixth Blog
 I started testing in the process stage of the application. The Type of testing i am doing as the application is being implemented is Uit testing.
 Testing  the smallest testable parts of an application, called units. The frame work I am using in the testing of the application is Junit.
 JUnit is a unit testing framework for the Java Language. It is a regression Testing framework used by android developers to create and execute unit tests in Java. The platforms main features include:
•	Fixtures
•	Test Suites
•	Test runners 
•	JUnit classes 

Even I am applying this methoogy in the development stage I was still getting errors.
Here is an example of the Junit test that I am running.

 @Test
    public void getdistance() throws Exception {

        double a = 1.0;
        double c = 1.0;
      MapsActivity m  =new MapsActivity();
      // ;
       assertEquals(a,m.getdistance(1.0,1.0),.1);

    }
    
 Here the test is passed when returing true.
 

## My Seventh Blog
   Creating the Conversations where the Employee/ Employer can view their List of conversation and access their messages,
   Conversations are displayed in the format of a list view, where each conversation has the Seders name, date, content of 
   message and a button for the Employee to delete the current conversation.  Conversations are displayed according the unique
   match up of User Ids in the database. Each user has a list of current conversation Ids displayed under there individual id. 
   To notify change in the data there is an event listener set on that field to signal the application in change of data. The event
   Listener is an interface in the View class that contains one single call back method, supporting interaction with the applications UI.  
   Each conversation does not match the default type of the List view which is a String therefore a customized Array Adapter has to be created 
   to support the display of custom objects. 
   This only displays the current sate of conversation in the current moment, so to establish use of the conversation’s functionality to link to
   the correct message field. To provide the correct link between messages I created two Array List’s
   that contain the user’s names and user’s ids corresponding to the correct index of conversations, so that when a conversation is clicked the Array
   Adapter will be able to reference the user by getting the clicked position in the array list.
   
   
## My Eight Blog
   Creted list of contacts for the Employee to access and different views, this was done using the same process as the Conversation List 
   By implementing onClick the Employee can start a conversation with that particular User.
   
## My ninth Blog
  The setting in jobder require the same state to be set to the placeholders in the settings Page, Using shared preferences allows me to save the data 
  locally on a file in the current device where the data can be pulled instantly at any time. The data here so for is mostly true/ false.
  For every User to be able to access each current state of value I named each saved vairable to the Users UID.
  
  Here is an example of inputting the Age value in settings to a file on the device.
  
  {
  
     SharedPreferences.Editor editor = sharedPref.edit();
                String key = FirebaseAuth.getInstance()
                        .getCurrentUser().getUid().toString() + "dis";
                editor.putInt(keyAge, cValue);
                editor.commit();
                
    }
    
    
  Here is an example of retrieving the data from the file.
               
               sharedPref.getString(FirebaseAuth.getInstance();
               

## My tenth Blog
  Created filestore for the Employee to upload Certificates e.g SafePass. 
  the images are uploaded to Firebase’s storages service with the link also uploaded with an object also consisting of the files name. 
  Each image in Firebase Storage has an attached Link to the image. So instesad of derictly downloading from the storage, the objects in the database 
  are downloaded , by passing the link of the image into Glide which is a service for dowloading images
  The List Adapter listens for an event change which one triggered will download all of the files for that specific user into the list adapter.
  
    Glide.with(getApplicationContext()).load(localFile).asBitmap().centerCrop().into(new BitmapImageViewTarget(image) {
                                                @Override
                                                protected void setResource(Bitmap resource) {
                                                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);

                                                    circularBitmapDrawable.setCircular(true);
                                                    image.setImageDrawable(circularBitmapDrawable);
                                                }
                                            });
                                            
                                            
    
    
    



  
## My eleventh Blog
Testing, I started to go back to testing as I wanted to make sure that every part I completed was functionally working,
and which would not lead to problems later on in the development process, I came accross errors when downloading the images the page was not curently 
refreshing this was a simple error but could have lead to a lot of difficulty further down in the development process.


  
## My twevth Blog
Fixed error on login when testing the web app out on chrome I was recieveing an error, which displayed a network error when connecting to firebase this was due to the 
connection with firebase by removing two div tags I was able to iliminate this, also using google chrome beta helped.
   
   
   
## My tirhteenth Blog 
 When testing the application when I would go to frequently test it, I was receiving the result of 0.0,0.0 for latitude and longitude values. 
Although I had tested the GPS provider and it was working perfectly I could not establish why I was receiving a result of 0.0. After studying the application and 
external factors associated with the application I found out what the Problem was. When I was testing the application inside buildings I was receiving a value of 0.0
 since the GPS signals were finding it difficult to penetrate through building material.
To fix the problem I had to fix my GPS Tracker class, I stopped the getLongitude() and getLatitude () function from pulling values when the location was null.
 
 public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }


    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

	
## My fourthenth Blog 
  To translate the  address of the Employers search I had to tranlate the address using GeoCoding 
  Geocoding is the process of converting addresses (like a street address) into geographic coordinates (like latitude and longitude), 
   try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return null;
            }
            
            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
	Here the latitude and longitude coordinates are pulled from the address
   
   
 ## My fifthenth Blog 
      I was having issuse's implementing the push notification,s I started 
	  implementing them locally on the device where event listners were listning 
	  for database changes. When there would be a change in the database the notification 
	  was excuted in the application locally.
	  However this would not work when the application was not running in the back round 
	  of the device.
	  Instead i resorted to hosting the listner on firebase clound functions, when the listner would 
	  see a change it would send a notification to the device corrsponding to its unique token.
	  
	  Here is a snippet of the firebase function to send notifications.
	   
  const request = change.after.val();
   console.log(request.token);
    var payload = {
        data: {
            title: request.messageUser,
            author: request.messageText
        }
    };

    // Send a message to devices subscribed to the provided topic.

     admin.messaging().sendToDevice(request.token, payload);
    })
	

## My Sixteenth Blog 
   I started t increase the frequency of my testing as I am now aware of the cost and resolution of errors 
   this late in the development stage. I started focusing more so on the functionality of the application 
   in terms of each action to reach its defined goal. There was a particular error that I focused on as it seemed very 
   common in android applications which was  ANR .This happens in the android application when the main UI thread of the app 
   is blocked or there is too much processes trying to run.
   To avoid this I reduced the excutions on the worker thread.
	  
	  
## My Seventeenth Blog
   I am working on cleaning up the web application using bootstarp, I was having a lot of difficulty fixing up layouts. I have included 
   design such as a breadcrum wrapper. When logging in to the web application I was recieving an error when connecting to firebase 
   suggesting a network error, This was due to the layout of my divs where the key of the firebase console 
   was not being called.
   
   
## My Eightheenth Blog 
   I started focusing on realtime satatistics for the Jobder application, when a new Employee was added to the database 
   a function on firebase would increment the stats in the web application fields, these stats are live and change wheneve the android 
   application changes the data in firbase here is a screenshot of the code.
  
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
   
   When a new Employee is regestered under plumbing, the webapplication fields are incremented 
   
	  
 

## My Nintheenth Blog Entry 
   When I had my application running I started to test the over all application 
   The testing that I felt most benificial was User testing, I was able to get feedback 
   on all main features of the application.
   I tested 16 people in my local parish which had a varience of age and professions.
   From the User cases the main issue that was highlighted was the Sign up. There was 5
   people out of the 16 unable to login, this was due to with two having no email address 
   and three with email address from different domains. For the Sign-up using Jobder the email 
   address must be a Gmail account e.g. adrianrabbitte@gmail.com where a different address e.g.
   adrianrabbitte@hotmail.ie would not be applicable.  As the application stands it is to late to
   change this feature, so to overcome this problem I have clearly stated in the login headings that
   the account must be Gmail. 
   
 
## My Twentieth Blog Entry 
    Fixing up testing document, and writing anaylisis on results.
	
	
## My 21st  Blog Entry 
    Finishing off technical manual and User Manual.
	
## Final Blog Entry
   I am just finishing up the final touches on the application, overall I am happy with the outcome of my application 
   where i feel like I have reached my goals and completed the application to the best of my ability. For sure there are 
   some things with the app that I would like to have changed or hopefully will implement in the future. If I was starting the application 
   again I would pay more detailed attention to following a software development plan (software process). This was the biggest project 
   that I had completed to date and did not realise the amount of work condisered when creating a full functional application, in terms of testing.
   It was not until during the middle of my application that I did not realise the importance of testing throughout the development lifecycle 
   and that identifing faults early would reduce the risk and cost of time further on in the development procees. I started to pay particular 
   attention to this factor when I was studying Software Engineering Principles during the semester, where we had a guest speaker in from the 
   Company Fineos. He Explined the importance of following a software process in terms of ROI and quaily of products for the Company.
   I feel that I would have applied an Agile aproach to the application as I would by frequently testing the application during the 
   development process.   I feel that if I hadnt applied testing throughout the development of the Application I would be in a lot of difficulty in 
   producing the working Application of Jobder.
   I feel that if i had to have followed a process with dicipline from the start like Agile it would have reduced the stress and the time envolved in 
   the creation of the application.
   
   Overall I am delighted to have a fully functional Application running that may have a postive effect on future users of the Application. 
   
  
	



