from exceptions.event_exceptions import InvalidEventException

class EventValidator:
    """
    Clasa care are rolul de a valida eveimentele
    """
    def validate(self,date,time,desc):
        """
        Valideaza o data, inainte de a fi introdusa
        :param date: data care se verifica
        :return: none
        :raises: InvalidEventException daca datele introduse nu sunt valide
        """
        error_list=[]
        #date part
        try:
            day,month,year=date.split(".")
            day=int(day)
            month=int(month)
            year=int(year)
            if day > 31 or day <= 0:
                error_list.append("Ziua trebuie sa fie in intervalul [1,31]")
            if month > 12 or month <= 0:
                error_list.append("Luna trebuie sa fie din intervalul [1,12]")
        except:
            error_list.append("Data furnizata nu respecta formatul")

        #hour part
        try:
            h,m=time.split(":")
            h=int(h)
            m=int(m)
            if h>23 or h<0:
                error_list.append("Ora furnizata trebuie sa apartina [0,24]")
            if m<0 or m>60:
                error_list.append("Minutele trebuie sa apartina [0,60]")
        except:
            error_list.append("Ora furnizata nu respecta formatul cerut")

        #desc part
        if desc=="":
            error_list.append("Descrierea nu poate fi vida")

        if error_list!=[]:
            error_string="\n".join(error_list)
            raise InvalidEventException(error_string)



