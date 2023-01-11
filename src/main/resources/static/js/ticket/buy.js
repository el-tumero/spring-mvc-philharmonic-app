const places = document.getElementsByClassName("place")
const chosenPlace = document.getElementById("chosenPlace")
const buyForm = document.getElementById("buyForm")
console.log(places)

let selectedPlace = null
let index = 0;

for(const place of places){
    if(index % 10 === 4) place.classList.add("fifth")
    index++;
    if(place.classList.contains("taken")) continue
    place.addEventListener("click", () => {
        if(selectedPlace == place) {
            selectedPlace = null
            chosenPlace.innerText = "Nie wybrano zadnego miejsca"
            buyForm.style.visibility = "hidden"
            buyForm.action = "#"
            place.classList.remove("selected")
            return
        }
        if(!selectedPlace){
            selectedPlace = place
            chosenPlace.innerText = "Wybrano miejsce: " + place.innerText
            buyForm.action = "/ticket/buy/" + place.id
            buyForm.style.visibility = "visible";
            place.classList.add("selected")
        }

    })
}





