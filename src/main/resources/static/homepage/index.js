

document.addEventListener('DOMContentLoaded', function () {

    const token = sessionStorage.getItem("token");
    const signInButton = document.getElementById('signInButton');
    const signOutButton = document.getElementById('signOutButton');
    const signOutLink = document.getElementById('signOutLink');

    const sign = document.getElementById('sign');
    const notSign = document.getElementById('notSign');


    // Function to update the visibility of buttons
    function updateButtonVisibility() {
        if (token) {
            signInButton.classList.add('d-none');
            signOutButton.classList.remove('d-none');
            sign.classList.remove('d-none');
        } else {
            signInButton.classList.remove('d-none');
            signOutButton.classList.add('d-none');
            notSign.classList.remove('d-none');
        }
    }

    // Initial check on page load
    updateButtonVisibility();

    // Handle Sign Out
    signOutLink.addEventListener('click', function (event) {
        event.preventDefault(); // Prevent default link behavior
        sessionStorage.removeItem("token"); // Remove the token
        window.location.href = "index.html"; // Redirect to home page or login page
    });


});

