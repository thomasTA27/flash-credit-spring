document.addEventListener("DOMContentLoaded", function () {

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

    fetch('http://localhost:8080/flash-credit/BorrowerServlet/getBorrower', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'basiqUserId': basiqUserId ,// Send borrowerId via headers
            'duration' :loanDuration,
            'amount' :loanDuration
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