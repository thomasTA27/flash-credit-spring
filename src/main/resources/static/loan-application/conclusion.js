function displaySessionStorage() {
    const orderedKeys = [
        "titleSession", "firstNameSession", "middleNameSession", "lastNameSession", // Show these first
        "dobSession",  "addressSession", "aptSession",
        "citySession", "stateSession", "postalCodeSession",
        "countrySession",  "mobileNumberSession",
        "reasonForLoan",
        "loanAmountInSession", "loanDurationInSession" // Show these last
    ];

    const keyLabels = {
        "titleSession": "Title",
        "firstNameSession": "First Name",
        "middleNameSession": "Middle Name",
        "lastNameSession": "Last Name",
        "dobSession": "Date of Birth",
        "addressSession": "Address",
        "aptSession": "Apartment/Suite",
        "citySession": "City",
        "stateSession": "State",
        "postalCodeSession": "Postal Code",
        "countrySession": "Country",
        "mobileNumberSession": "Mobile Number",
        "reasonForLoan": "Reason for Loan",
        "loanAmountInSession": "Loan Amount",
        "loanDurationInSession": "Loan Duration"
    };

    $("#sessionList").empty();

    // Append each session item using jQuery
    orderedKeys.forEach(key => {
        const value = sessionStorage.getItem(key);
        if (value !== null) {
            const displayKey = keyLabels[key] || key;
            $("#sessionList").append(`<li class = "text-bg-danger">${displayKey}: ${value} </li>`);
        }
    });

    // const sessionList = document.getElementById('sessionList');
    // sessionList.innerHTML = ''; // Clear any existing items
    // orderedKeys.forEach(key => {
    //     const value = sessionStorage.getItem(key);
    //     if (value !== null) {
    //         const displayKey = keyLabels[key] || key;
    //         const listItem = document.createElement('li');
    //         sessionList.textContent = `${displayKey}: ${value}`;
    //         sessionList.appendChild(listItem);
    //     }
    // });
}

$("#finish").click(function (){
    alert("hello")

})


window.onbeforeunload = function (event) {
    if (basiqUserId) {
        // Show warning before exit
        event.preventDefault();
        // event.returnValue = "Are you sure you want to leave? Your application will be lost.";

        // Send delete request to backend
        navigator.sendBeacon('http://localhost:8080/borrower/deleteBorrowerDetail',
            JSON.stringify({ basiqUserId: basiqUserId })
        );
    }



};

// Call the function on page load
window.onload = displaySessionStorage;