#include <iostream>
#include <omp.h>

using namespace std;

const int MAX = 20;
int a[MAX], b[MAX], m[MAX][MAX], c[MAX];

int produsScalar(int a[MAX], int b[MAX]) {
    int s = 0;

    for (int i = 0; i < MAX; i++) {
        s += a[i] * b[i];
    }

    return s;
}

int produsScalarParallel(int a[MAX], int b[MAX]) {
    int sum = 0;
    int partial_sum = 0;

#pragma omp parallel firstprivate(partial_sum) num_threads(16)  // firstprivate: ca fork, se creeaza o copie a lui partial_sum
    // in partial_sum va fi suma elementelor de pe chunk-ul procesat
    {
#pragma omp for
        for (int i = 0; i < MAX; i++) {
            partial_sum += a[i] * b[i];
        }

#pragma omp critical
        {
            sum += partial_sum;
        }
    }

    return sum;
}

int produsScalarParallel2(int a[MAX], int b[MAX]) {
    int sum = 0;

#pragma omp parallel for reduction(+:sum)   // reduction: iti permite sa aduni valorile variabilelor; ca un fel de GROUP BY pe SQL
    // +: ce se face cu variabila sum la final, se aduna
    // sum: variabila "agregata"
    for (int i = 0; i < MAX; i++) {
        sum += a[i] * b[i];
    }

    return sum;
}

void addVectors(int a[MAX], int b[MAX], int c[MAX])
{
    for (int i = 0; i < MAX; i++) {
        c[i] = a[i] + b[i];
    }
}

void multiplyVectors(int a[MAX], int b[MAX], int c[MAX])
{
    for (int i = 0; i < MAX; i++) {
        c[i] = a[i] * b[i];
    }
}

int main()
{
    omp_set_num_threads(4); // setam nr. de thread-uri pe care, ulterior, le va crea OpenMP

    cout << "Thread num1: " << omp_get_num_threads() << endl;

#pragma omp parallel num_threads(16)    // omp parallel -> se creeaza thread-urile; num_threads(16) suprascrie num_threads(4)-le de mai sus, dar DOAR in sectiunea asta!
    {

        cout << "Thread num2: " << omp_get_num_threads() << endl;

#pragma omp critical    // bucata de aici se executa pe rand, ceva de genul lock/unlock
        {
            cout << "Hello from: " << omp_get_thread_num() << endl;
            cout << "Thread num3: " << omp_get_num_threads() << endl;
        }

        cout << "Thread num4: " << omp_get_num_threads() << endl;

        // omp single: bucata se executa o singura data, de un singur thread

#pragma omp for schedule(static, 2) nowait  // schedule: modul in care vrem sa fie spart vectorul; schedule static face spargerea cum am facut pana acum la laburi (n/p)
                                            // dar, daca ii dam (static, 2), sparge in intervale de cate 2 elemente, si va face de atatea ori cat trebuie pentru a echivala vectorul
                                            // dynamic ar face ceva in genul unui thread pool
                                            // nowait: daca nu punem nowait, firul de executie nu continua pana nu termina toate thread-urile treaba asta; facem codul non-blocking
        for (int i = 0; i < MAX; i++) {
            a[i] = omp_get_thread_num();
            b[i] = i;
        }

#pragma omp for schedule(static, 2) collapse(2) // daca nu puneam collapse, paraleliza doar primul for (ar fi fost doar liniile distribuite intre threads);
        // asa, se paralelizeaza ambele
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                m[i][j] = omp_get_thread_num();
            }
        }
    }

    for (int i = 0; i < MAX; i++) {
        cout << a[i] << " ";
    }

    cout << endl;

    for (int i = 0; i < MAX; i++) {
        for (int j = 0; j < MAX; j++) {
            cout << m[i][j] << " ";
        }
        cout << endl;
    }

    cout << produsScalar(a, b) << endl;
    cout << produsScalarParallel(a, b) << endl;
    cout << produsScalarParallel2(a, b) << endl;

#pragma omp parallel num_threads(8)
    {
#pragma omp sections    // sections: aici se vor da sectiunile ce vor fi executate
        {
#pragma omp section // cate un thread executa un section, deci avem 3 sections => 3 thread-uri folosite
            {
                addVectors(a, b, c);
#pragma omp critical
                {
                    cout << "Section add: " << omp_get_thread_num() << endl;
                }
            }
#pragma omp section
            {
                multiplyVectors(a, b, c);
#pragma omp critical
                {
                    cout << "Section multiply: " << omp_get_thread_num() << endl;
                }
            }
#pragma omp section
            {
                produsScalar(a, b);
#pragma omp critical
                {
                    cout << "Section produs: " << omp_get_thread_num() << endl;
                }
            }
        }
    }

#pragma omp single
    {
        cout << "Forta steaua: " << omp_get_thread_num() << endl;
    }

    return 0;
}
