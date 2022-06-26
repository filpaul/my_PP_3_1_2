const postNewUser = document.querySelector('#postNewUser')
const navUsersTab = document. querySelector('#nav-users-tab')
const emailNew = document.querySelector('#email-new')
const passwordNew = document.querySelector('#password-new')
const nameNew = document.querySelector('#name-new')
const surNameNew = document.querySelector('#surName-new')
const ageNew = document.querySelector('#age-new')

postNewUser.addEventListener('submit', (e) => {
    e.preventDefault()
    let readyUrl;
    let values = getSelectValues(inputRolesNew)
    if(values.length === 0) {
        readyUrl = urlNewUser
    } else {
        readyUrl = urlNewUser + '?inputRoles=' + values
    }
    fetch(readyUrl,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            'email': emailNew.value,
            'password': passwordNew.value,
            'name': nameNew.value,
            'surName': surNameNew.value,
            'age': ageNew.value
        })
    })
        .then(res => res.json())
        .then(res => {
            console.log(res)
            navUsersTab.click()
            showUser(res, tableBodyAdmin)
            emailNew.value = ''
            passwordNew.value = ''
            nameNew.value = ''
            surNameNew.value = ''
            ageNew.value = ''
        })
})

function getSelectValues(select) {
    var result = [];
    var options = select && select.options;
    var opt;

    for (var i=0, iLen=options.length; i<iLen; i++) {
        opt = options[i];

        if (opt.selected) {
            result.push(opt.value);
        }
    }
    return result;
}