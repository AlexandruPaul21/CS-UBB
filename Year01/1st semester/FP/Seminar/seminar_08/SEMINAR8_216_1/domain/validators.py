from domain.entities import Product
from exceptions.exceptions import ValidationException


class ProductValidator:
    """
        clasa pentru incapsularea algoritmului de validare
        (validate putea fi metoda in clasa Product - choice of design)
        """

    def validate(self, product):
        errors = []
        if product.getName() == '':
            errors.append("Denumirea produsului nu poate fi vida.")

        if product.getCountry() not in ['Germania', 'Austria', 'Spania', 'Italia', 'Austria', 'Turcia', 'Belgia',
                                        'Elvetia', 'Romania', 'SUA', 'Moldova']:
            errors.append("Tara de origine a produsului nu este recunoscuta.")

        if len(errors) > 0:
            # error_string = '\n'.join(errors)
            raise ValidationException(errors)
            # raise ValidationException


class ShopValidator:
    def validate(self, shop):
        errors = []
        if shop.getName() == '':
            errors.append("Denumirea magazinului nu poate fi vida.")

        if shop.getLocation() == '':
            errors.append("Locatia magazinului nu poate fi vida.")

        if len(errors) > 0:
            # error_string = '\n'.join(errors)
            raise ValidationException(errors)
            # raise ValidationException


class SaleItemValidator:
    def validate(self, sale_item):
        errors = []
        if sale_item.getPret() < 0:
            errors.append("Pretul nu poate fi mai mic ca 0.")

        if sale_item.getStoc() < 0 and sale_item.getStoc() > 250:
            errors.append("Stocul trebuie sa fie intre 0 si 250.")

        if len(errors) > 0:
            # error_string = '\n'.join(errors)
            raise ValidationException(errors)
            # raise ValidationException


def test_validate_product():
    validator = ProductValidator()
    p = Product('1', 'jeleuri', 'Germania')
    validator.validate(p)

    p1 = Product('2', '', 'Germania')
    try:
        validator.validate(p1)
        assert False
    except ValidationException:
        assert True

    p2 = Product('3', 'acadele', 'Portugalia')

    try:
        validator.validate(p2)
        assert False
    except ValidationException:
        assert True


def test_validate_shop():
    pass


def test_validate_sale_item():
    pass
