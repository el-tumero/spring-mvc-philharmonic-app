const cancelButtons = document.getElementsByClassName("cancel")
const cancelForm = document.getElementById("cancelForm")

for(const button of cancelButtons){
    button.addEventListener("click", () => {
        if(confirm("Czy na pewno chcesz zrezygnować z biletu?")){
            cancelForm.action = "/ticket/cancel/" + button.id
            cancelForm.submit()
        }
    })
}


