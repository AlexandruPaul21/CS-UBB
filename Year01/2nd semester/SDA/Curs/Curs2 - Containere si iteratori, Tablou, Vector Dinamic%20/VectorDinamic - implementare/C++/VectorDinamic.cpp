#include "VectorDinamic.h"
void VectorDinamic::redim() {
	//alocam un spatiu de capacitate dubla
	//copiem vechile valori in noua zona
	//dublam capacitatea
	//eliberam vechea zona
	//vectorul indica spre noua zona
}
VectorDinamic::VectorDinamic(int cp) {
	//alocam spatiu de memorare pentru vector
	//setam numarul de elemente
}
VectorDinamic::~VectorDinamic() {
}
int VectorDinamic::dim() const{
}
TElem VectorDinamic::element(int i) const{
}
void VectorDinamic::adaugaSfarsit(TElem e) {
}
Iterator VectorDinamic::iterator() {
Iterator::Iterator(const VectorDinamic& _v) : v(_v) {
	//varianta 2
}
void Iterator::prim() {
	//varianta 2
}
bool Iterator::valid() const{
	//varianta 2
}
TElem Iterator::element() const{
	//varianta 2
}

void Iterator::urmator() {
}