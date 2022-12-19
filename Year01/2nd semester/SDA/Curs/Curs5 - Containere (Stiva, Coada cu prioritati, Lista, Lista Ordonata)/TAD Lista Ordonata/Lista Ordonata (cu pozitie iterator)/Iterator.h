#pragma once
#include "LO.h"

class LO;

class Iterator{
	friend class LO;
private:
	const LO& lo;
	PNod curent;

	Iterator(const LO& l);
public:
	void prim();
	void urmator();
	bool valid() const;
    TElem element() const;

};


