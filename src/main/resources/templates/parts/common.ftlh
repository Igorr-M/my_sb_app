<#macro page>
    <!DOCTYPE HTML>
    <html>
    <head>
        <title>My_SB_App</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="/static/style.css" />

        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous" />
        <link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css" rel="stylesheet" />
        <script src="https://www.google.com/recaptcha/api.js" async="true" defer="true"></script>
<#--        <script defer src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js"></script>-->
    </head>
    <body>
        <#include "navbar.ftlh">
        <div class="container text-left mt-5">
            <#nested>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<#--        Збереження прокрутки на сторінці-->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Повернення прокрутки
                const scrollY = sessionStorage.getItem('scrollY');
                if (scrollY !== null) {
                    window.scrollTo(0, parseInt(scrollY));
                    sessionStorage.removeItem('scrollY');
                }

                // Збереження прокрутки при кліку на кнопку лайка
                document.addEventListener('click', function (e) {
                    const target = e.target;
                    if (target.matches('.like-button') || target.closest('.like-button')) {
                        sessionStorage.setItem('scrollY', window.scrollY);
                    }
                });
            });
        </script>

    </body>
    </html>
</#macro>