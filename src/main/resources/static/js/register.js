const password = document.getElementById("password")
const email = document.getElementById("email")
const passwordInfo = document.getElementById("passwordInfo")
const emailInfo = document.getElementById("emailInfo")
//const submit = document.getElementByClassName("registerButton")[0]

let passwordCorrect = false;
let emailCorrect = false;

function unlockButton() {
    if(passwordCorrect && emailCorrect) {
        console.log("ok")
    }
}


email.addEventListener("input", e => {
    if(!e.target.value.includes("@")){
        emailCorrect = false
        emailInfo.innerText = "Adres nie posiada znaku @!"
        emailInfo.style.color = "red"
    }

    if(e.target.value.includes("@")){
            emailCorrect = true
            emailInfo.innerText = ""
    }

    if(e.target.value.length >= 255){
        emailCorrect = false
        emailInfo.innerText = "Email zbyt długi (max 255 znaków)"
        emailInfo.style.color = "red"
    }
})

password.addEventListener("input", e => {
    if(e.target.value.length < 8){
        passwordCorrect = false
        passwordInfo.innerText = "Hasło zbyt krótkie (minimum 8 znaków)"
        passwordInfo.style.color = "red"
    }

    if(e.target.value.length >= 8 && e.target.value.length < 255){
            passwordCorrect = true
            passwordInfo.innerText = ""
    }

    if(e.target.value.length >= 255){
        passwordCorrect = false
        passwordInfo.innerText = "Hasło zbyt długie (max 255 znaków)"
         passwordInfo.style.color = "red"
    }
})