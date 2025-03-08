document.addEventListener("DOMContentLoaded", function () {


    const signOutLink = document.getElementById('signOutLink');


    signOutLink.addEventListener('click', function (event) {
        event.preventDefault(); // Prevent default link behavior
        alert("you logout you will get direct to homepage")
        sessionStorage.removeItem("token"); // Remove the token
        window.location.href = "../index.html"; // Redirect to home page or login page
    });


        getBorrowerData();

    const continueButtpon = this.getElementById("continue");

    continueButtpon.addEventListener('click' , function (){

        document.location.href = "conclusion.html"; //
    })

} ) ;


function getBorrowerData() {

    const basiqUserId = sessionStorage.getItem('basiqUserId');

    const loanDuration = sessionStorage.getItem('loanDurationInSession');
    const loanAmount = sessionStorage.getItem('loanAmountInSession');


    function displayAccounts(accounts) {
        const container = document.getElementById("accountsContainer");
        container.innerHTML = ""; // Clear previous content
        document.getElementById("placeholder").style.display = "none"; // Hide placeholder

        accounts.forEach(account => {
            const accountHTML = `
                <div class="col-md-4">
                    <div class="card shadow-sm p-3 account-card">
                        <div class="card-body">
                            <h5 class="card-title text-primary">${account.name}</h5>
                            <p class="card-text"><strong>Account Number:</strong> ${account.id}</p>
                            <p class="card-text"><strong>Balance:</strong> ${account.balance}</p>
                            <p class="card-text"><strong>Avalable fund:</strong> ${account.availableFunds}</p>
                        </div>
                    </div>
                </div>
            `;
            container.innerHTML += accountHTML;
        });
    }

    fetch('http://localhost:8080/api/borrowers/getListAccount', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'basiqUserId': basiqUserId
        },

    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Parse JSON response
        })
        .then(data => {
            console.log("Server response:", data);

            if (Array.isArray(data) && data.length > 0) {
                displayAccounts(data); // Call function to display accounts
            } else {
                console.warn("No accounts found in response.");
                document.getElementById("placeholder").innerHTML = "<p>No accounts available</p>";
            }
        })

        .catch(error => {
            console.error("Error sending borrower data:", error);
        });
}
    function displayAccounts(data) {
        const placeholder = document.getElementById("placeholder");
        placeholder.innerHTML = ""; // Clear previous content

        data.forEach(account => {
            const listItem = document.createElement("li");
            listItem.className = "bg-secondary text-white py-4 mt-5";
            listItem.textContent = `ID: ${account.id}, Name: ${account.name}, Account No: ${account.accountNo} , balance is : ${account.balance}`;
            placeholder.appendChild(listItem);

            const breakpoint = document.createElement("br");
            placeholder.appendChild(breakpoint);

        });

}