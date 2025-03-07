	
class PopupManager {
    constructor() {
        this.popup = null;
        this.popupTimer = null;
    }

    openPopup(url, width = 800, height = 600) {
        // Calculate center position for the popup
        const left = window.screen.width / 2 - width / 2;
        const top = window.screen.height / 2 - height / 2;

        // Open popup
        if (!this.popup || this.popup.closed) {
            this.popup = window.open(url, "YouTubePopup", `width=${width},height=${height},left=${left},top=${top}`);
            
            // Start monitoring the popup
            this.startMonitoring();
        } else {
            this.popup.focus(); // Bring existing window to the front
        }

        // Handle popup blocked
        if (!this.popup) {
            console.error("Popup was blocked by the browser");
            return;
        }
    }
    startMonitoring() {
        this.popupTimer = setInterval(() => {
            try {
                // Check if popup is closed
                if (!this.popup || this.popup.closed) {
                    console.log("Popup was closed by the user bye");


                let basiqUserId = sessionStorage.getItem('basiqUserId');

                sendBorrowerData(basiqUserId);
                    //we shouuld get the active or not
                    this.cleanup();
                    return;
                }
                console.log("Popup is still open hi hi ");

            } catch (error) {
                // Cross-origin errors will occur while the popup is on another domain
                // This is normal and we should continue monitoring
                if (!error.message.includes("cross-origin")) {
                    this.cleanup();
                    console.error("Error monitoring popup:", error);
                }
            }
        }, 1000); // Check every 1 second
    }

    cleanup() {
        // Clear the monitoring timer
        if (this.popupTimer) {
            clearInterval(this.popupTimer);
            this.popupTimer = null;
        }
    }
}


var connection = null;

function sendBorrowerData(basiqUserId) {

    var loanAmount = sessionStorage.getItem('loanAmountInSession');
    var loanTenure = sessionStorage.getItem('loanDurationInSession');
    fetch('http://localhost:8080/api/borrowers/basiqBankConnectionStatus', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify( {
            basiqUserId : basiqUserId,
            loanAmount : loanAmount,
            loanTenure : loanTenure

        } )
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json(); // Parse JSON response
    })
    .then(data => {
        console.log("Server response:", data);


        if (data.status) {
            console.log("Received status:", data.status);

            
            sessionStorage.setItem('statusOfConnection', data.status);

            connection = data.status;


if (connection === "active") {
    let countdown = 3; // Set the initial countdown time
    const countdownDisplay = document.getElementById('countdown'); // Ensure you have an element to display the countdown

    // Update the countdown every second
    const countdownInterval = setInterval(() => {
        countdownDisplay.textContent = countdown; // Update the countdown text on the page
        countdown--;

        // When the countdown reaches 0, change the page
        if (countdown < 0) {
            clearInterval(countdownInterval); // Stop the countdown
            document.location.href = "displayAccount.html"; // Redirect to the new page
        }
    }, 1000); // Run every 1 second
}

        } else {
            console.warn("tokenbBounce not found in response.");
        }
    })
    .catch(error => {
        console.error("Error sending borrower data:", error);
    });
}


document.addEventListener('DOMContentLoaded', function() {
    const loanAmount = sessionStorage.getItem('loanAmountInSession');
    const loanTenure = sessionStorage.getItem('loanDurationInSession');

    // Check if values exist
    if (loanAmount && loanTenure) {
        console.log(`Loan Amount: AUD ${loanAmount}`);
        console.log(`Loan Tenure: ${loanTenure} months`);
        // You can also display these values on the page if needed
        document.getElementById('temp-loan-use-info').textContent = 
            `Loan Amount: AUD ${loanAmount}, Loan Tenure: ${loanTenure} months`;
    } else {
        console.error('No loan data found in sessionStorage.');
    }

document.getElementById("test").addEventListener("click", () => {

    const popupManager = new PopupManager();

    const tokenBouncId = sessionStorage.getItem('tokenBounce');


    popupManager.openPopup("https://consent.basiq.io/home?token=" + tokenBouncId);


});

});

