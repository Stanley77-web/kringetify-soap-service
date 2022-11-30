# Kringetify Soap Service



## Deskripsi

Repository ini merupakan service soap untuk Kringetify Premium dengan menggunakan Jax-WS
## Run
Gunakan script `docker build -t webservice:lastest .` pada terminal untuk build soap service<br>
Gunakan script `docker-compose up` pada terminal untuk database masukkan <br><br><br>
Tembak endpoint `http://localhost:4790/ws/subscription?wsdl` pada postman untuk melakukan request service subscription<br>
- Request create buat envelope xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey</apikey>
    </Header>
    <Body>
        <create xmlns="http://www.kringetify.com/ws/">
            <creatorId xmlns="">[int]</creatorId>
            <subscriberId xmlns="">[int]</subscriberId>
        </create>
    </Body>
  </Envelope>
```
- Request approal buat envelope xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey</apikey>
    </Header>
    <Body>
        <approval xmlns="http://www.kringetify.com/ws/">
            <creatorId xmlns="">[int]</creatorId>
            <subscriberId xmlns="">[int]</subscriberId>
            <approval xmlns="">[boolean]</approval>
        </approval>
    </Body>
  </Envelope>
```
- Request pending subs buat envelope xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey</apikey>
    </Header>
    <Body>
        <pendingstatus xmlns="http://www.kringetify.com/ws/"/>
    </Body>
  </Envelope>
```
- Request semua subs buat envelope xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey</apikey>
    </Header>
    <Body>
        <allstatus xmlns="http://www.kringetify.com/ws/"/>
    </Body>
  </Envelope>
```

<br><br>
Tembak endpoint `http://localhost:4790/ws/checkstatus?wsdl` pada postman untuk melakukan request service check status<br>
- Request check status subs buat envelope xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey</apikey>
    </Header>
    <Body>
        <subscriptionstatus xmlns="http://www.kringetify.com/ws/">
            <creatorId xmlns="">[int]</creatorId>
            <subscriberId xmlns="">[int]</subscriberId>
        </subscriptionstatus>
    </Body>
  </Envelope>
```
- Request check user subs buat envelope xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey</apikey>
    </Header>
    <Body>
        <subscriptions xmlns="http://ws.kringetify.com/">
            <subscriberId xmlns="">[int]</subscriberId>
        </subscriptions>
    </Body>
  </Envelope>
```


## Spefisikasi
- Java 8
- Mysql
## Screenshot

## Pembagian Tugas
