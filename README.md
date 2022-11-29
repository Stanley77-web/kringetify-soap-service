# Kringetify Soap Service



## Deskripsi

Repository ini merupakan service soap untuk Kringetify Premium dengan menggunakan Jax-WS
## Run
Untuk database masukkan script ini pada terminal
`docker-compose up` <br>
Untuk soap service masukkan script ini pada terminal
`java -jar soap-service.jar`<br>
Untuk melakukan request tembak endpoint `http://localhost:4790/ws/subscription?wsdl` pada postman<br>
- Untuk request approval buat body request dalam xml seperti dibawah ini<br>
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey/apikey>
    </Header>
    <Body>
        <createSubscription xmlns="http://ws.kringetify.com/">
            <creator_id xmlns="">1</creator_id>
            <subscriber_id xmlns="">2</subscriber_id>
        </createSubscription>
    </Body>
  </Envelope>
```
- Untuk request create buat body dalam xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey/apikey>
    </Header>
    <Body>
        <makeApproval xmlns="http://ws.kringetify.com/">
            <creator_id xmlns="">[int]</creator_id>
            <subscriber_id xmlns="">[int]</subscriber_id>
            <approval xmlns="">[boolean]</approval>
        </makeApproval>
    </Body>
  </Envelope>
```
- Untuk request pending subs buat body dalam xml seperti dibawah ini
```
  <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <apikey>apikey/apikey>
    </Header>
    <Body>
        <pendingSubscription xmlns="http://ws.kringetify.com/"/>
    </Body>
  </Envelope>
```



## Spefisikasi
- Java 8
- Mysql
## Screenshot

## Pembagian Tugas
