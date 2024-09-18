const userCrudHtml = 'http://localhost:8080/api/users'

//Ждем загрузки всех элементов перед исполнением внутренних операций
document.addEventListener('DOMContentLoaded', async function () {
    //авторизованный пользователь
    let userAuth = await responseUserAuth()
    //список всех пользователей
    let usersList = await responseUserList()

    //извлекает роли и подгружает информацию необходимую для отображения стартовой страницы
    let roleList = userAuth.roles
    if(roleList.includes('ADMIN')){
        console.log('Data received usersList: ', usersList)
        fillingUsersTable(usersList)
    }
    initHeadersFieldsUser(userAuth)
    fillingAuthUserTable(userAuth)

    //реализация переключения кнопок ADMIN и USER
    var buttons = document.querySelectorAll('button[data-target]');
    buttons.forEach(function (button) {
        button.addEventListener('click', function () {
            var target = button.getAttribute('data-target');
            var contentSections = document.querySelectorAll('.content');
            contentSections.forEach(function (section) {
                section.style.display = 'none';
            });
            var targetSection = document.querySelector(target);
            if (targetSection) {
                targetSection.style.display = 'block';
            }
        });
    });
    var adminButton = document.getElementById('adminButton');
    if (adminButton) {
        adminButton.click();
    }
    //Функция добавления нового пользователя
    (function (){
        let form = $('#formNewUser')
        async function handleFormSubmit(event){
            event.preventDefault()
            let formData = serializeForm(form.get(0))
            console.log('Сериализуемые данные - ',formData)
            let response = sendForm(formData)
        }
        form.on('submit', handleFormSubmit)

        //Отправляем сериализованный обьект на сервер
        async function sendForm(data){
            let errorText = $('#error-message')
            let response = await fetch(userCrudHtml, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            })
            if((await response).ok){
                console.log('Пользователь занесен в базу: ', response);
                usersList = await responseUserList()
                fillingUsersTable(usersList)

                document.getElementById('new-user').classList.remove('show', 'active');
                document.getElementById('users-table').classList.add('show', 'active');

                // Обновляем активную вкладку в навигации
                document.getElementById('new-user-tab').classList.remove('active');
                document.getElementById('users-table-tab').classList.add('active');
                form.get(0).reset();
                errorText.css('display', 'none');
            }
            else {
                let errorResponse = await response.json()
                errorText.text(errorResponse.info);
                errorText.css('display', 'block');
            }
        }
    })()
})


//добавляем в шапку сайта информацию об авторизованном пользователе
function initHeadersFieldsUser(userAuth){
    fetchUserAuth()
    function fetchUserAuth() {
        let infoEmailUserAuth = document.getElementById('emailUserAuth')
        let infoRolesUserAuth = document.getElementById('rolesUserAuth')
        infoRolesUserAuth.innerText = 'with roles: ' + userAuth.roles.join(' ')
        infoEmailUserAuth.innerText = userAuth.email
    }
}

//Заполняем таблицу информации об авторизованном пользователе
function fillingAuthUserTable(userAuth){
    const userButton = $('#userButton')
    userButton.on('click', function () {
        let table = $('#authUserTable')
        let row = $('<tr>')
        row.html(`<td>${userAuth.id}</td>
                        <td>${userAuth.name}</td>
                        <td>${userAuth.lastname}</td>
                        <td>${userAuth.age}</td>
                        <td>${userAuth.email}</td>
                        <td>${userAuth.roles.join(' ')}</td>`)
        table.append(row)
    })
}

//Заполняем таблицу пользователей
function fillingUsersTable(usersList){

    createUserTable(usersList)
    onClickDeleteUser()
    onClickEditUser()

    function createUserTable(userList) {
        let tableBody = $('#users-table-place tbody')
        if (tableBody.length === 0) {
            tableBody = $('<tbody></tbody>').appendTo('#users-table-place')
        }
        tableBody.empty()
        usersList.forEach(user => {
            let row = $('<tr>')
            row.html(`<td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.lastname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.join(' ')}</td>
                        <td>
                            <a class="btn btn-info"
                                id="edit-user"
                                style="padding-right: 12px; padding-left: 12px; color: aliceblue;"
                                data-id="${user.id}">
                                Edit
                            </a>
                        </td>
                        <td>
                            <a class="btn btn-danger delete-btn"
                                id="delete-user"
                                style="padding-right: 12px; padding-left: 12px;"
                                data-id="${user.id}">
                                Delete
                            </a>
                        </td>`)
            tableBody.append(row)
        })
    }
    //Добавление слежения за кликом EDIT внутри таблицы пользователей
    function onClickEditUser(){
        $('body').on('click', '.btn-info', async function () {
            console.log('Получем пользователя по id')
            let userId = $(this).data('id')
            let userEdit = await getUserById(userId)
            fillingTheModalEditUser(userEdit)
            $('#userEditModal').on('show.bs.modal', function () {
                // Очищаем текст сообщения об ошибке и скрываем его
                $('#editErrorMessage').text('').hide();
            });
        });
    }
    //Добавление слежения за кликом DELETE внутри таблицы пользователей
    function onClickDeleteUser(){
        $('body').on('click', '.delete-btn', async function () {
            console.log('Получем пользователя по id')
            let userId = $(this).data('id')
            let userDelete = await getUserById(userId)
            fillingTheModalDeleteUser(userDelete)
        });
    }
    //Добавление данных в модальное окно для изменения данных
    function fillingTheModalEditUser(user){
        let modalUserInfo = $('#userEditModal')
        $('#editUserID').val(user.id);
        $('#editUsername').val(user.name);
        $('#editUserLastname').val(user.lastname);
        $('#editUserAge').val(user.age);
        $('#editUserEmail').val(user.email);
        modalUserInfo.modal('show');

    }
    //Добавление данных в модальное окно для удаления пользователя
    function fillingTheModalDeleteUser(user){
        let modalUserInfo = $('#userDeleteModal')
        $('#deleteUserID').val(user.id);
        $('#deleteUsername').val(user.name);
        $('#deleteLastname').val(user.lastname);
        $('#deleteUserAge').val(user.age);
        $('#deleteUserEmail').val(user.email);
        modalUserInfo.modal('show');

    }
    //Добавление слежения за кликом Edit внутри модального окна
    (function () {
        let form = $('#formEditUser')

        async function handleFormSubmit(event) {
            event.preventDefault()
            let formData = serializeForm(form.get(0))
            formData['id']=$('#editUserID').val()
            console.log('Сериализуемые данные - ',formData)
            let response = editUser(formData)
        }
        form.on('submit', handleFormSubmit)
    })()

    //Добавление слежения за кликом Delete внутри модального окна
    $('#confirmDeleteBtn').on('click', function () {
        let userId = $('#deleteUserID').val();  // Получаем ID пользователя для удаления
        let messages = deleteUser(userId)
    });
}
//Отправляем на сервер запрос на изменение данных о пользователе
async function editUser(data){
    let errorText = $('#editErrorMessage')

    let response = await fetch(userCrudHtml,{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json' // Указываем, что не отправляем тело
        },
        body: JSON.stringify(data)
    })
    if (response.ok) {
        //очищаем модальное окно
        $('#formEditUser')[0].reset();
        errorText.css('display', 'none').text('');

        // Закрываем модальное окно
        $('#userEditModal').modal('hide');
        // Обновляем таблицу пользователей
        fillingUsersTable(await responseUserList());
    } else {
        let errorResponse = await response.json()
        errorText.text(errorResponse.info);
        errorText.css('display', 'block');
    }
}
//Отправляем на сервер запрос на удаление пользователя
async function deleteUser(id){
    console.log('Удаляем пользователя с id', id)
    let response = await fetch(`${userCrudHtml}/${id}`,{
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json' // Указываем, что не отправляем тело
        }
        })
    if (response.ok) {
        // Закрываем модальное окно
        $('#userDeleteModal').modal('hide');
        // Обновляем таблицу пользователей
        fillingUsersTable(await responseUserList());
    } else {
        let errorText = await response.text();
        console.error('Ошибка удаления: ', errorText);
    }
}
//асинхронный запрос на сервер для получения пользователя по id
async function getUserById(id){
    console.log('Получем пользователя по id', id)
    let response = await fetch(`${userCrudHtml}/${id}`)
        .then(response => {
            const data = response.json()
            return data
        })
        .then(user => {
            console.log('Data received userAuth_response: ', user)
            return user
        })
        .catch(error => {
            console.error('Ошибка:', error);
        })
    return response
}
// Асинхронная функция для запроса данных всех пользователей с сервера
async function responseUserList() {
    let response = await fetch(userCrudHtml)
        .then(response => {
            const data = response.json()
            return data
        })
        .then(usersList => {
            console.log('Data received usersResponse: ', usersList)
            return usersList
        })
        .catch(error => {
            console.error('Ошибка:', error);
        })
    return response
}
// Асинхронная функция для запроса данных о авторизованном пользователе с сервера
async function responseUserAuth() {
    const response = await fetch('http://localhost:8080/api/currentUser')
        .then(response => {
            const data = response.json()
            return data
        })
        .then(user => {
            console.log('Data received userAuth_response: ', user)
            return user
        })
        .catch(error => {
            console.error('Ошибка:', error);
        })
    return response
}
//Сериализуем данные формы
function serializeForm(formNode){
    let data = new FormData(formNode);

    let result = {};
    let rolesArray = [];

    for (let [key, value] of data.entries()) {
        if (key === 'userRoles') {
            rolesArray.push({ userRole: value }); // Если это роль, добавляем в массив ролей
        } else {
            result[key] = value; // Остальные поля добавляем напрямую
        }
    }
    // Добавляем массив ролей в итоговый объект данных
    result['roles'] = rolesArray;
    return result;
}

