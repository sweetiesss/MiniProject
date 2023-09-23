$(document).ready(function() {
    function handleFormSubmission(formId, apiUrl) {
        $(formId).submit(function(event) {
            event.preventDefault();

            var formData = new FormData($(this)[0]);

            $.ajax({
                url: apiUrl,
                type: 'POST',
                data: formData,
                async: false,
                success: function(response) {
                    alert('Code: ' + response.code);
                },
                cache: false,
                contentType: false,
                processData: false
            });
        });
    }

    handleFormSubmission("#uploadCustomerForm", "/api/customer/csv");
    handleFormSubmission("#uploadApartmentForm", "/api/apartment/csv");
    handleFormSubmission("#uploadContractForm", "/api/contract/csv");
});