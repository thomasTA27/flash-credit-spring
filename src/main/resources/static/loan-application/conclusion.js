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

    // $("#sessionList").empty();

    // Append each session item using jQuery
    // orderedKeys.forEach(key => {
    //     const value = sessionStorage.getItem(key);
    //     if (value !== null) {
    //         const displayKey = keyLabels[key] || key;
    //         $("#sessionList").append(`<li class = "text-bg-danger">${displayKey}: ${value} </li>`);
    //     }
    // });

    $("#sessionTableBody").empty();

    orderedKeys.forEach(key => {
        const value = sessionStorage.getItem(key);
        if (value !== null) {
            const displayKey = keyLabels[key] || key;
            $("#sessionTableBody").append(`
                    <tr>
                        <td class="fw-bold">${displayKey}</td>
                        <td class="text-secondary">${value}</td>
                    </tr>
                `);
        }
    });
}
let shouldSendRequest = true;
$("#finish").click(function (){
    // alert("hello")let shouldSendRequest = true;

    shouldSendRequest = false;
    window.location.href ="../index.html"


})


window.onbeforeunload = function (event) {
    var phone = sessionStorage.getItem("mobileNumberSession");
    var basiqUserId = sessionStorage.getItem("basiqUserId");

    if (basiqUserId && shouldSendRequest) {
        // Show warning before exit
        event.preventDefault();

        // Send delete request to backend using fetch
        fetch('http://localhost:8080/api/borrowers/deleteBorrowerDetail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'  // Ensure Content-Type is JSON
            },
            body: JSON.stringify({
                basiqUserId: basiqUserId,
                phone: phone
            })
        })
            .then(response => response.json())
            .then(data => console.log("Response:", data))
            .catch(error => console.error('Error:', error));
    }
};
// Call the function on page load
window.onload = displaySessionStorage;