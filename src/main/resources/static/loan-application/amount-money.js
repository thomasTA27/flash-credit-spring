
document.addEventListener('DOMContentLoaded', function() {
    // Format number as currency
    function formatCurrency(value) {
        return new Intl.NumberFormat('au-AU', {
            style: 'currency',
            currency: 'aud',
            maximumFractionDigits: 0
        }).format(value).replace('aud', 'aud');
    }

    // Loan Amount Slider
    const loanAmountSlider = document.getElementById('loanAmount');
    const loanAmountValue = document.getElementById('loanAmountValue');

    loanAmountSlider.addEventListener('input', function() {
        loanAmountValue.textContent = formatCurrency(this.value);
    });

    // Loan Tenure Slider
    const loanTenureSlider = document.getElementById('loanTenure');
    const loanTenureValue = document.getElementById('loanTenureValue')

    loanTenureSlider.addEventListener('input', function() {
        loanTenureValue.textContent = `${this.value} month${this.value > 1 ? 's' : ''}`;
    });
    
    const continueButton = document.getElementById('amount-money-submit');
    continueButton.addEventListener('click', function (event) {
        // Prevent the default navigation behavior
        event.preventDefault();

        // Get the values of loan amount and loan tenure
         const loanAmount = loanAmountSlider.value;
         const loanTenure = loanTenureSlider.value;
         const selectedOption = document.querySelector('input[name="accordionChoice"]:checked');
        //  console.log( "option is " +  selectedOption.value);
         console.log("this is loan inside " + loanAmount);
         
         
        //  const reasonForLoan = selectedOption.value;
      

         if(selectedOption ){
            
            console.log("this is loan reason " + selectedOption.value);
            
         }
         else{
            alert("Please select a reason for loan");
            return;
         }
         const reasonForLoan = selectedOption.value;
        //  console.log("this is loan reason haha " + reasonForLoan);

         sessionStorage.clear();
         sessionStorage.setItem('loanAmountInSession', loanAmount);
         sessionStorage.setItem('loanDurationInSession', loanTenure);
         sessionStorage.setItem('reasonForLoan', reasonForLoan);
        //  sessionStorage.removeItem('loanAmountInSession');


         document.location.href = "personal-info.html";

    });
});