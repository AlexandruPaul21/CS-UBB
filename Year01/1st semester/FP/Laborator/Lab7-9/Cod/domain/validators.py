from utils.utils import *
from domain.entities import movie,client
from datetime import date

class movieValidator:
    def validate(self,film):
        errors=[]
        if film.getTitle()=="":
            errors.append("Titlul nu poate fi vid")
        if film.getGen()=="":
            errors.append("Genul nu poate fi vid")
        if film.getDesc()=="":
            errors.append("Descrierea nu poate fi vida")
        if len(film.getDesc())>10000:
            errors.append("Lungimea descrierii este prea mare")
        if len(film.getGen())>100:
            errors.append("Lungimea genului este prea mare")

        if len(errors)>0:
            error_string= '\n'.join(errors)
            raise ValueError(error_string)

class clientValidator:
    def validate(self,customer):
        errors=[]
        if customer.getName()=="":
            errors.append("Numele nu poate fi vid")
        if customer.getCNP()=="":
            errors.append("CNP-ul nu poate fi vid")
        try:
            value=int(customer.getCNP())
        except:
            errors.append("CNP-ul trebuie sa fie format doar din cifre")
            value=0
        CNP=[]
        while value!=0:
            CNP.append(value%10)
            value//=10
        #CNP=reversed(CNP)
        if len(CNP)>13:
            msg="Lungime invalida "+str(len(CNP))
            errors.append(msg)
        # elif CNP[12]==0:
        #     errors.append("Format invalid")
        # if date.today().year-getYear_from_CNP(CNP)<18:
        #     errors.append("Clientul trebuie sa aiba peste 18 ani")
        # if getMonth_from_CNP(CNP)>12 or getDay_from_CNP(CNP)>31 :
        #     errors.append("CNP invalid")

        if len(errors)>0:
            error_string= '\n'.join(errors)
            raise ValueError(error_string)