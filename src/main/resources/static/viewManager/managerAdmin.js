// Function to populate the table
function populateTable() {

    fetch('http://localhost:8080/api/borrowers/getAllBorrower', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            console.log(response)
            return response.json(); // Parse JSON respons
            // e
        })
        .then(apiData => {
            const tableBody = document.getElementById("loanTableBody");

            // Clear existing rows (if any)
            tableBody.innerHTML = "";

            // Populate the table with fetched data
            apiData.forEach((item) => {
                const row = document.createElement("tr");

                // Add firstName
                const firstNameCell = document.createElement("td");
                firstNameCell.textContent = item.firstName;
                row.appendChild(firstNameCell);

                // Add lastName
                const lastNameCell = document.createElement("td");
                lastNameCell.textContent = item.lastName;
                row.appendChild(lastNameCell);

                // Add durationInWeeks
                const durationCell = document.createElement("td");
                durationCell.textContent = item.durationInWeeks;
                row.appendChild(durationCell);

                // Add loanAmount
                const loanAmountCell = document.createElement("td");
                loanAmountCell.textContent = `$${item.loanAmount.toLocaleString()}`;
                row.appendChild(loanAmountCell);

                // Add Detail button
                const actionCell = document.createElement("td");
                const detailButton = document.createElement("button");
                detailButton.textContent = "Detail";
                detailButton.className = "btn btn-primary";
                detailButton.addEventListener("click", () => {
                    alert(
                        `Details for ${item.firstName} ${item.lastName}:\nDuration: ${item.durationInWeeks} weeks\nLoan Amount: $${item.loanAmount.toLocaleString()}`
                    );
                });
                actionCell.appendChild(detailButton);
                row.appendChild(actionCell);

                // Append the row to the table
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error fetching data:", error);
            alert("Failed to fetch data. Please try again later.");
        });
}

// Call the function to populate the table when the page loads
window.onload = populateTable;



// Make sure the DOM is fully loaded before selecting elements
document.addEventListener("DOMContentLoaded", function () {
    // Select the signOut element using its correct ID
    var signOut = document.getElementById("signOut");

    // Check if the element exists before adding an event listener
    if (signOut) {
        signOut.addEventListener("click", function (event) {
            event.preventDefault(); // Prevent default link behavior
            alert("You are logging out. Redirecting to homepage...");
            sessionStorage.removeItem("token"); // Remove the authentication token
            window.location.href = "../index.html"; // Redirect to the homepage
        });
    }


});