const email = document.getElementById("email")
const password = document.getElementById("password")
const loginBtn = document.getElementById("login_btn")

loginBtn.addEventListener("click", async() => {
    const data = {
        email: email.value,
        password: password.value
    }
    console.log(data)
    const response = await fetch("/auth", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    console.log(...response.headers)
})