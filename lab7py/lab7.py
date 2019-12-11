from math import exp
from random import random


def getInput():
# citirea params pt reteaua neuronala
    numarEpoci = 1000 #input("Numarul epocilor :")
    numarNeuroni = 10 #input("Numarul neuronilor :")
    eroareaMaxima = 0.1 #input("Eroarea maxima :")
    rataInvatare =0.5 #input("Rata de invatare :")
    return (numarEpoci,numarNeuroni,eroareaMaxima,rataInvatare)


def readFromFile():
    f = open(r"C:\Disk_D\inputData.txt", "r")
    line = f.readline().strip()
    params = line.split(" ")
    listaIntrare=list()
    listaIesire=list()
    temporarListInput = list()
    temporarListOutput = list()

    # citirea parametrilor din fisier si salvarea lor intr=-o lista de site
    for i in range(0, int(params[2])):

        temporarListInput.append(f.read(13))
        for elem in temporarListInput:
            li = list(elem)
            li2 = list(filter(lambda el : el!=" ",li ))
            li3 = list(map(lambda el: int(el), li2))
        listaIntrare.append(li3)

        temporarListOutput.clear()
        temporarListOutput.append(f.readline().strip())
        for elem in temporarListOutput:
            li = list(elem)
            li2 = list(filter(lambda el: el != " ", li))
            li3 = list(map(lambda el: int(el), li2))
        listaIesire.append(li3)
    return (params[0], params[1], params[2], listaIntrare, listaIesire)

readFromFile()

# Initialize a network
def initialize_network(n_inputs, n_hidden, n_outputs):
    network = list()
    hidden_layer = [{'weights':[random() for i in range(n_inputs + 1)]} for i in range(n_hidden)]
    network.append(hidden_layer)
    output_layer = [{'weights':[random() for i in range(n_hidden + 1)]} for i in range(n_outputs)]
    network.append(output_layer)
    return network


# Calculate neuron activation for an input
def activate(weights, inputs):
    activation = weights[-1]
    for i in range(len(weights)-1):
        activation += weights[i] * float(inputs[i])
    return activation


# Transfer neuron activation
def transfer(activation):
    return 1.0 / (1.0 + exp(-activation))


def forward_propagate(network, row):
    inputs = row
    for layer in network:
        new_inputs = list()
        new_inputs.clear()
        for neuron in layer:
            activation = activate(neuron['weights'], inputs)
            neuron['output'] = transfer(activation)
            new_inputs.append(neuron['output'])
        inputs = new_inputs
    return inputs


def transfer_derivative(output):
    return output * (1.0 - output)


def backward_propagate_error(network, expected):
    for i in reversed(range(len(network))):
        layer = network[i]
        errors = list()
        if i != len(network)-1:
            for j in range(len(layer)):
                error = 0.0
                for neuron in network[i + 1]:
                    error += (float(neuron['weights'][j]) * float(neuron['delta']))
                errors.append(error)
        else:
            for j in range(len(layer)):
                neuron = layer[j]
                errors.append(float(expected[j]) - neuron['output'])
        for j in range(len(layer)):
            neuron = layer[j]
            neuron['delta'] = errors[j] * transfer_derivative(neuron['output'])


def update_weights(network, row, l_rate):
    for i in range(len(network)):
        inputs = row
        if i != 0:
            inputs = [neuron['output'] for neuron in network[i - 1]]
        for neuron in network[i]:
            for j in range(len(inputs)):
                neuron['weights'][j] += l_rate * neuron['delta'] * float(inputs[j])
            neuron['weights'][-1] += l_rate * neuron['delta']


def train_network(network, train, l_rate, n_epoch, n_outputs,maxErr):
    for epoch in range(n_epoch):
        sum_error = 0
        for row in train:
            outputs = forward_propagate(network, row[0])
            expected = row[1]
            sum_error += sum([(expected[i] - outputs[i]) ** 2 for i in range(n_outputs)])
            backward_propagate_error(network, expected)
            update_weights(network, row[0], l_rate)
        print('>epoch=%d, lrate=%.3f, error=%.3f' % (epoch, l_rate, sum_error))
    if(sum_error>maxErr):
        print("eroarea e mai mare ca eroarea maxima, ne oprim")
        exit(-1)



def predict(network, row):
    outputs = forward_propagate(network, row)
    return outputs.index(max(outputs))


def main():

    numarEpoci, numarNeuroni, eroareaMaxima, rataInvatarep = getInput()
    numarIntrari,numarIesiri, numarVectori, listaIntrari,listaIesiri = readFromFile()

    numarIntrari= int(numarIntrari)
    numarIesiri=int(numarIesiri)
    numarEpoci = int(numarEpoci)
    numarNeuroni = int(numarNeuroni)
    eroareaMaxima = float(eroareaMaxima)
    rataInvatarep = float(rataInvatarep)
    numarVectori = int(numarVectori)


    retea = initialize_network(numarIntrari, numarNeuroni, numarIesiri)

    data = list()
    for i in range(0, numarVectori):
        data.append([listaIntrari[i], listaIesiri[i]])

    train_network(retea, data, rataInvatarep, numarEpoci, numarIesiri,eroareaMaxima)

    for row in data:
        print(row[0])
        print(row[1])
        prediction = predict(retea, row[0])
        expected = row[1].index(1)
        print('Expected=%d, Got=%d' % (expected, prediction))


main()