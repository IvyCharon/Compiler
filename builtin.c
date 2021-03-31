#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void print(char* str) {
    printf("%s", str);
}

void println(char* str) {
    printf("%s\n", str);
}

void printInt(int x) {
    printf("%d", x);
}

void printlnInt(int x) {
    printf("%d\n", x);
}

char* getString() {
    char* tmp = (char*)malloc(sizeof(char) * 1000);
    scanf("%s", tmp);
    return tmp;
}

int getInt() {
    int tmp;
    scanf("%d", &tmp);
    return tmp;
}

char* toString(int x) {
    char *tmp = (char*)malloc(sizeof(char) * 12);
    sprintf(tmp, "%d", x);
    return tmp;
}

int __array_size(char* arr) {
    return *(((int*)arr) - 1);
}

int __string_length(char* str) {
    return strlen(str);
}

char* __string_substring(char* str, int left, int right) {
    char* tmp = (char*)malloc((right - left + 1) * sizeof(char));
    for(int i = left; i < right; ++ i) {
        tmp[i - left] = str[i];
    }
    tmp[right - left] = '\0';
    return tmp;
}

int parseInt(char* str) {
    int tmp;
    sscanf(str, "%d", &tmp);
    return tmp;
}

int ord(char* str, int ord) {
    return str[ord];
}

char* __string_add(char* s1, char* s2) {
    int l1 = strlen(s1), l2 = strlen(s2);
    char* tmp = (char*)malloc((l1 + l2 + 1) * sizeof(char));
    for(int i = 0;i < l1; ++ i)
        tmp[i] = s1[i];
    for(int i = 0;i < l2; ++ i) {
        tmp[l1 + i] = s2[i];
    }
    tmp[l1 + l2] = '\0';
    return tmp;
}

int __string_equal(char* s1, char* s2) {
    return !strcmp(s1, s2);
}

int __string_not_equal(char* s1, char* s2) {
    return strcmp(s1, s2);
}

int __stirng_smaller(char* s1, char* s2) {
    return strcmp(s1, s2) < 0;
}

int __string_bigger(char* s1, char* s2) {
    return strcmp(s1, s2) > 0;
}

int __string_smaller_equal(char* s1, char* s2) {
    return strcmp(s1, s2) <= 0;
}

int __string_bigger_equal(char* s1, char* s2) {
    return strcmp(s1, s2) >= 0;
}