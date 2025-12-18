# Diplom__3
Настройка файла browser.properties 
В файле browser.properties указываются параметры для запуска браузера в тестах.

перед запуском тестов скачать с https://github.com/yandex/YandexDriver/releases актуальную версию yandexdriver для установленного браузера на вашем ПК (моя версия 25.10.0-stable для Windows) скачанный исполняемый файл помещаем в проект в browser.properties

Варианты настроек Для запуска тестов на Яндекс.Браузере:

browser=yandex yandex.browser.path="C:\Users\roman\WebDriver\Yandex\YandexBrowser\Application\yandexdriver.exe" Для запуска тестов на Google Chrome:

browser=chrome

На Windows: Обычно исполняемый файл для Яндекс.Браузера находится по пути: C:\Program Files\Yandex\YandexBrowser\Application\browser.exe

или

C:\Users\ваше_имя\AppData\Local\Yandex\YandexBrowser\Application\YandexBrowser.exe
