function prueba(){

var data = null;

var xhr = new XMLHttpRequest();

xhr.addEventListener("readystatechange", function () {
  if (this.readyState === 4) {
    alert(this.responseText);
  }
});

xhr.open("GET", "http://localhost/pdg1");


xhr.send(data);

	
}
