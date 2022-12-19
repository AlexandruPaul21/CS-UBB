from exceptions.domain_exceptions import DomainException

class ShowValidator:
    """
    Clasa Validtor, are rolul de a facilita validarea obiectelor
    """
    def validate(self,show):
        """
        Functia valideaza un show
        :param show: show-ul de validat
        :return: None
        :raises: DomainException daca show-ul nu este valid
        """
        title=show.getTitle()
        artist=show.getArtist()
        gen=show.getGen()
        time=show.getTime()

        errors_list=[]
        if title=="":
            errors_list.append("Titlul nu poate fi vid")
        if artist=="":
            errors_list.append("Artistul nu poate fi vid")
        if not (gen in ["Comedie","Concert","Balet","Altele"]):
            errors_list.append("Genul trebuie sa fie unul dintre: Comedie,Concert,Balet,Altele")
        if time<0:
            errors_list.append("Timpul nu poate fi negativ")

        if errors_list!=[]:
            errors_string='\n'.join(errors_list)
            raise DomainException(errors_string)