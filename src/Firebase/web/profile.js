//establishing key
var config = {
  apiKey: "AIzaSyD0FXoIvlAGjKdeWu9Q2oyEQFy9fiE1Obs",
  authDomain: "fir-e6d19.firebaseapp.com",
  databaseURL: "https://fir-e6d19.firebaseio.com",
  projectId: "fir-e6d19",
  storageBucket: "gs://fir-e6d19.appspot.com",
  messagingSenderId: "395552461617"
};
  firebase.initializeApp(config);

  //
  // window.addEventListener("load", getData(genFunction));
  //    function getData(callbackIN) {
  //      var ref = firebase.database().ref("data");
  //      ref.once('value').then(function (snapshot) {
  //        callbackIN(snapshot.val())
  //      });
  //    }
  //    function genFunction(data) {
  //      var cdata = [];
  //      var len = data.length;
  //      for(var i=1; i<len; i++) {
  //        cdata.push({
  //          label: data[i]['label'],
  //          value: data[i]['value']
  //        });
  //      }
  //    var firebaseChart = new FusionCharts({
  //        type: 'area2d',
  //        renderAt: 'chart-container',
  //        width: '650',
  //        height: '400',
  //        dataFormat: 'json',
  //        dataSource: {
  //            "chart": {
  //                "caption": "Website Visitors Trend",
  //                "subCaption": "Last 7 Days{br}ACME Inc.",
  //                "subCaptionFontBold": "0",
  //                "captionFontSize": "20",
  //                "subCaptionFontSize": "17",
  //                "captionPadding": "15",
  //                "captionFontColor": "#8C8C8C",
  //                "baseFontSize": "14",
  //                "baseFont": "Barlow",
  //                "canvasBgAlpha": "0",
  //                "bgColor": "#FFFFFF",
  //                "bgAlpha": "100",
  //                "showBorder": "0",
  //                "showCanvasBorder": "0",
  //                "showPlotBorder": "0",
  //                "showAlternateHGridColor": "0",
  //                "usePlotGradientColor": "0",
  //                "paletteColors": "#6AC1A5",
  //                "showValues": "0",
  //                "divLineAlpha": "5",
  //                "showAxisLines": "1",
  //                "drawAnchors": "0",
  //                "xAxisLineColor": "#8C8C8C",
  //                "xAxisLineThickness": "0.7",
  //                "xAxisLineAlpha": "50",
  //                "yAxisLineColor": "#8C8C8C",
  //                "yAxisLineThickness": "0.7",
  //                "yAxisLineAlpha": "50",
  //                "baseFontColor": "#8C8C8C",
  //                "toolTipBgColor": "#FA8D67",
  //                "toolTipPadding": "10",
  //                "toolTipColor": "#FFFFFF",
  //                "toolTipBorderRadius": "3",
  //                "toolTipBorderAlpha": "0",
  //                "drawCrossLine": "1",
  //                "crossLineColor": "#8C8C8C",
  //                "crossLineAlpha": "60",
  //                "crossLineThickness": "0.7",
  //                "alignCaptionWithCanvas": "1"
  //            },
  //            "data": cdata
  //        }
  //    });
  //    firebaseChart.render();





firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
    // User is signed in.
 var database = firebase.database();





    var user = firebase.auth().currentUser.uid;
    var storageRef = firebase.storage();
    var pathReference = storageRef.ref(user  + '/Profile.jpg');
       question:string="";
    firebase.database().ref('/'+ "Users"+ '/' +user +'/'+"Info" + '/' ).once('value',(snapshot) =>{
    var name;
    this.question = snapshot.val().fName;
 document.getElementById('id1').innerHTML = this.question;
    console.log(this.question);

  });
 
    document.getElementById("user_div").style.display = "block";
    document.getElementById("login_div").style.display = "block";

    var user = firebase.auth().currentUser;

    if(user != null){

      var email_id = user.email;
      document.getElementById("user_para").innerHTML = "Welcome User : " + email_id;
        window.location.href='profile.html'
    }

  } else {
    // No user is signed in.
	document.getElementById("user_div").style.display = "none";
    document.getElementById("login_div").style.display = "block";



  }
});

function hide()
{
  if(document.getElementById("sidenav").style.display != 'block')
  {
    w3_open()
  }
  else {
    {
      w3_close()
    }
  }
}
function w3_open() {
    document.getElementById("sidenav").style.display = "block";
}
function w3_close() {
    document.getElementById("sidenav").style.display = "none";
}




function logout(){
 firebase.auth().signOut();
    window.location.href='index.html'
}
