
//https://firebase.google.com/docs/web/setup
var config = {
  apiKey: "AIzaSyD0FXoIvlAGjKdeWu9Q2oyEQFy9fiE1Obs",
  authDomain: "fir-e6d19.firebaseapp.com",
  databaseURL: "https://fir-e6d19.firebaseio.com",
  projectId: "fir-e6d19",
  storageBucket: "",
  messagingSenderId: "395552461617"
};
  firebase.initializeApp(config);

  
  
 //https://www.youtube.com/watch?v=iKlWaUszxB4&t=152s followed tuturial for login 
firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
    // User is signed in.

    document.getElementById("user_div").style.display = "block";
    document.getElementById("login_div").style.display = "block";

    var user = firebase.auth().currentUser;

    if(user != null){

      var email_id = user.email;
      document.getElementById("user_para").innerHTML = "Welcome User : " + email_id;
        window.location.href='stats.html'
    }

  } else {
    // No user is signed in.
	document.getElementById("user_div").style.display = "none";
    document.getElementById("login_div").style.display = "block";



  }
});


//https://www.youtube.com/watch?v=iKlWaUszxB4&t=152s
function login(){

  var userEmail = document.getElementById("email_field").value;
  var userPass = document.getElementById("password_field").value;

  firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
    // Handle Errors here.
    var errorCode = error.code;
    var errorMessage = error.message;

    window.alert("Error : " + errorMessage);

    // ...
  });

}


//https://www.youtube.com/watch?v=iKlWaUszxB4&t=152s
function logout(){
  firebase.auth().signOut();
    window.location.href='index.html'
}

function toggleSlidebar()
{
  document.getElementById("side-nav").classList.toggle('active');
}
