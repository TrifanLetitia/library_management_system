document.addEventListener('DOMContentLoaded', function () {
    fetchBorrowedBooks();
});

let loanList = [[]]

async function fetchBorrowedBooks() {
    const userDetails = localStorage.getItem('userDetails');
    const obj = JSON.parse(userDetails);

    var id = obj.id;

    const apiURL = `http://localhost:8080/imprumuturi/${id}/user`;
    try {
        const response = await fetch(apiURL);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const loans = await response.json();
        loanList = loans
        displayBorrowedBooks(loans)
    } catch (error) {
        console.error('Could not fetch books:', error);
    }
}

console.log(localStorage.getItem('userDetails'))

console.log(loanList)

function displayBorrowedBooks(loans){
    const container = document.getElementById('booksDisplay');
        container.innerHTML = '';
        loans.forEach(loan => {
            const loanId = loan[0].imprumut.id;
            const loanDate = loan[0].imprumut.dataImprumut;
            
            const loanDiv = document.createElement('div');
            loanDiv.className = 'imprumut';
            loanDiv.innerHTML = `<strong>ID Imprumut: ${loanId}, Data: ${loanDate}</strong></strong>`;
            
            const bookList = document.createElement('ul');
            loan.forEach(book => {
                const bookId = book.exemplar.id;
                const listItem = document.createElement('li');
                listItem.textContent = `ID Exemplar: ${bookId}`;
                bookList.appendChild(listItem);
            });
            const returnButton = document.createElement('button');
                returnButton.textContent = 'Returnează';
                returnButton.onclick = function() { returnBooks(loanId, loan); }; // Assuming `returnBooks` is a function you'll define
                
                loanDiv.appendChild(bookList);
                loanDiv.appendChild(returnButton);
                container.appendChild(loanDiv);
        });
}

function getTodayDate() {
    const date = new Date();
    return date.toLocaleDateString('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: '2-digit'
    });
}

async function returnBooks(loanId, loan) {
    console.log(`Returning books for loan ID: ${loanId}`);

    loan.forEach(async (book) => {
        console.log(book.exemplar.id)
        const status = 'returnata'
        const apiURL = `http://localhost:8080/exemplare/${book.exemplar.id}/updateStatus`;
        try {
            const response = await fetch(apiURL, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(status)
            });
            console.log(status)
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const responseData = await response.json();
            console.log(`Book ID: ${book.id} returned successfully`, responseData);
        } catch (error) {
            console.error(`Could not return book ID: ${book.id}`, error);
        }
    });

    const todayDate = getTodayDate();
    console.log(todayDate); 
    console.log(loanId)
    const apiURL2 = `http://localhost:8080/imprumuturi/${loanId}/updateDate`;
        try {
            const response = await fetch(apiURL2, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(todayDate)
            });
            setTimeout(() => {
                window.location.reload();
            }, 1000); // Delay de 1000ms (1 secundă)
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const responseData = await response.json();

        } catch (error) {
        
        }

}

function resetDetails(){
    localStorage.removeItem('userDetails');
}