from domain.entities import Product


class ValidationException(Exception):
    pass

class ProductValidator:
    """
        clasa pentru incapsularea algoritmului de validare
        (validate putea fi metoda in clasa Product - choice of design)
        """
    def validate(self, product):
        errors = []
        if product.getName() == '':
            errors.append("Denumirea produsului nu poate fi vida.")
        if product.getStoc()< 0:
            errors.append("Numarul de unitati din stoc nu poate fi negativ.")
        if product.getPret() < 0:
            errors.append("Pretul nu poate fi negativ.")

        if len(errors) > 0:
            error_string = '\n'.join(errors)
            raise ValueError(error_string)
            # raise ValidationException


def test_validate_product():
    validator = ProductValidator()
    p = Product('jeleuri', 10, 12.5)
    validator.validate(p)

    p1 = Product('', -10, 12.5)
    try:
        validator.validate(p1)
        assert False
    except ValueError:
        assert True

    p2 = Product('acadele', 6, -4)

    try:
        validator.validate(p2)
        assert False
    except ValueError:
        assert True
