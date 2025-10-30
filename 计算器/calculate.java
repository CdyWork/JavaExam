import React, { useState, useRef, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import * as math from 'mathjs';

const CasioCalculator = () => {
  const [display, setDisplay] = useState('0');
  const [mode, setMode] = useState('normal'); // normal, matrix, equation, graph
  const [history, setHistory] = useState([]);
  const [matrixA, setMatrixA] = useState([[0, 0], [0, 0]]);
  const [matrixB, setMatrixB] = useState([[0, 0], [0, 0]]);
  const [equation, setEquation] = useState('');
  const [graphFunc, setGraphFunc] = useState('sin(x)');
  const [graphData, setGraphData] = useState([]);
  const [memory, setMemory] = useState(0);
  const [angleMode, setAngleMode] = useState('deg'); // deg or rad
  const [secondFunction, setSecondFunction] = useState(false);

  // 普通计算
  const handleNumberClick = (num) => {
    if (mode === 'normal') {
      if (display === '0' || display === 'Error') {
        setDisplay(num.toString());
      } else {
        setDisplay(display + num);
      }
    }
  };

  const handleOperatorClick = (op) => {
    if (mode === 'normal') {
      setDisplay(display + op);
    }
  };

  const calculate = () => {
    try {
      let expr = display;
      
      // 处理角度/弧度
      if (angleMode === 'deg') {
        expr = expr.replace(/sin\(/g, 'sin((pi/180)*');
        expr = expr.replace(/cos\(/g, 'cos((pi/180)*');
        expr = expr.replace(/tan\(/g, 'tan((pi/180)*');
      }
      
      const result = math.evaluate(expr);
      const resultStr = typeof result === 'number' ? result.toString() : JSON.stringify(result);
      setHistory([...history, { expr: display, result: resultStr }]);
      setDisplay(resultStr);
    } catch (error) {
      setDisplay('Error');
    }
  };

  const clearDisplay = () => {
    setDisplay('0');
  };

  const deleteLastChar = () => {
    if (display.length > 1) {
      setDisplay(display.slice(0, -1));
    } else {
      setDisplay('0');
    }
  };

  // 科学函数
  const handleScientific = (func) => {
    try {
      let result;
      const val = parseFloat(display);
      
      switch(func) {
        case 'sin':
          result = angleMode === 'deg' ? math.sin(val * Math.PI / 180) : math.sin(val);
          break;
        case 'cos':
          result = angleMode === 'deg' ? math.cos(val * Math.PI / 180) : math.cos(val);
          break;
        case 'tan':
          result = angleMode === 'deg' ? math.tan(val * Math.PI / 180) : math.tan(val);
          break;
        case 'sqrt':
          result = math.sqrt(val);
          break;
        case 'square':
          result = val * val;
          break;
        case 'ln':
          result = math.log(val);
          break;
        case 'log':
          result = math.log10(val);
          break;
        case 'exp':
          result = math.exp(val);
          break;
        case 'abs':
          result = math.abs(val);
          break;
        case 'factorial':
          result = math.factorial(Math.floor(val));
          break;
        default:
          return;
      }
      setDisplay(result.toString());
    } catch (error) {
      setDisplay('Error');
    }
  };

  // 矩阵运算
  const updateMatrix = (matrix, row, col, value, setter) => {
    const newMatrix = matrix.map(r => [...r]);
    newMatrix[row][col] = parseFloat(value) || 0;
    setter(newMatrix);
  };

  const matrixOperation = (op) => {
    try {
      let result;
      switch(op) {
        case 'add':
          result = math.add(matrixA, matrixB);
          break;
        case 'subtract':
          result = math.subtract(matrixA, matrixB);
          break;
        case 'multiply':
          result = math.multiply(matrixA, matrixB);
          break;
        case 'det':
          result = math.det(matrixA);
          break;
        case 'inv':
          result = math.inv(matrixA);
          break;
        case 'transpose':
          result = math.transpose(matrixA);
          break;
      }
      setDisplay(JSON.stringify(result));
    } catch (error) {
      setDisplay('Error: ' + error.message);
    }
  };

  // 方程求解
  const solveEquation = () => {
    try {
      // 解线性方程 ax + b = 0
      if (equation.includes('=')) {
        const [left, right] = equation.split('=');
        const expr = `${left}-(${right})`;
        const solution = math.rationalize(expr);
        setDisplay(`Solution: ${solution}`);
      } else {
        // 求根
        const roots = math.evaluate(`nthRoot(${equation}, 2)`);
        setDisplay(`Roots: ${JSON.stringify(roots)}`);
      }
    } catch (error) {
      setDisplay('Error: ' + error.message);
    }
  };

  // 绘图功能
  const plotGraph = () => {
    try {
      const data = [];
      for (let x = -10; x <= 10; x += 0.2) {
        const scope = { x: x };
        const y = math.evaluate(graphFunc, scope);
        if (typeof y === 'number' && !isNaN(y) && isFinite(y)) {
          data.push({ x: parseFloat(x.toFixed(2)), y: parseFloat(y.toFixed(2)) });
        }
      }
      setGraphData(data);
    } catch (error) {
      setDisplay('Error in function');
    }
  };

  useEffect(() => {
    if (mode === 'graph') {
      plotGraph();
    }
  }, [mode, graphFunc]);

  // 内存操作
  const memoryAdd = () => {
    try {
      const val = parseFloat(display);
      setMemory(memory + val);
    } catch (error) {}
  };

  const memoryRecall = () => {
    setDisplay(memory.toString());
  };

  const memoryClear = () => {
    setMemory(0);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-800 to-gray-900 p-4">
      <div className="max-w-6xl mx-auto bg-gray-900 rounded-3xl shadow-2xl overflow-hidden border-4 border-gray-700">
        {/* 顶部品牌 */}
        <div className="bg-gradient-to-r from-blue-900 to-blue-800 p-3 border-b-2 border-gray-700">
          <div className="flex justify-between items-center">
            <span className="text-white font-bold text-xl tracking-wider">CASIO fx-991CN X</span>
            <span className="text-blue-300 text-xs">{angleMode === 'deg' ? 'DEG' : 'RAD'} | M: {memory !== 0 ? '✓' : '-'}</span>
          </div>
        </div>

        {/* 模式选择 */}
        <div className="bg-gray-800 p-2 flex gap-2 border-b border-gray-700">
          {['normal', 'matrix', 'equation', 'graph'].map(m => (
            <button
              key={m}
              onClick={() => setMode(m)}
              className={`px-4 py-2 rounded-lg font-semibold text-sm transition-all ${
                mode === m 
                  ? 'bg-blue-600 text-white shadow-lg' 
                  : 'bg-gray-700 text-gray-300 hover:bg-gray-600'
              }`}
            >
              {m === 'normal' ? '普通' : m === 'matrix' ? '矩阵' : m === 'equation' ? '方程' : '绘图'}
            </button>
          ))}
        </div>

        <div className="flex">
          {/* 主计算区域 */}
          <div className="flex-1 p-4">
            {/* 显示屏 */}
            <div className="bg-green-100 rounded-lg p-4 mb-4 border-4 border-green-900 shadow-inner">
              <div className="text-right text-3xl font-mono text-green-900 break-all min-h-12">
                {display}
              </div>
              <div className="text-right text-xs text-green-700 mt-1">
                {secondFunction && <span className="font-bold">2nd</span>}
              </div>
            </div>

            {/* 普通模式 */}
            {mode === 'normal' && (
              <div className="space-y-2">
                {/* 科学函数行 */}
                <div className="grid grid-cols-5 gap-2">
                  <button onClick={() => handleScientific('sin')} className="btn-function">sin</button>
                  <button onClick={() => handleScientific('cos')} className="btn-function">cos</button>
                  <button onClick={() => handleScientific('tan')} className="btn-function">tan</button>
                  <button onClick={() => handleScientific('sqrt')} className="btn-function">√</button>
                  <button onClick={() => handleScientific('square')} className="btn-function">x²</button>
                </div>
                
                <div className="grid grid-cols-5 gap-2">
                  <button onClick={() => handleScientific('ln')} className="btn-function">ln</button>
                  <button onClick={() => handleScientific('log')} className="btn-function">log</button>
                  <button onClick={() => handleScientific('exp')} className="btn-function">eˣ</button>
                  <button onClick={() => handleScientific('abs')} className="btn-function">|x|</button>
                  <button onClick={() => handleScientific('factorial')} className="btn-function">n!</button>
                </div>

                {/* 数字和运算符 */}
                <div className="grid grid-cols-4 gap-2">
                  <button onClick={clearDisplay} className="btn-special col-span-2">AC</button>
                  <button onClick={deleteLastChar} className="btn-special">DEL</button>
                  <button onClick={() => handleOperatorClick('/')} className="btn-operator">/</button>
                </div>

                <div className="grid grid-cols-4 gap-2">
                  <button onClick={() => handleNumberClick(7)} className="btn-number">7</button>
                  <button onClick={() => handleNumberClick(8)} className="btn-number">8</button>
                  <button onClick={() => handleNumberClick(9)} className="btn-number">9</button>
                  <button onClick={() => handleOperatorClick('*')} className="btn-operator">×</button>
                </div>

                <div className="grid grid-cols-4 gap-2">
                  <button onClick={() => handleNumberClick(4)} className="btn-number">4</button>
                  <button onClick={() => handleNumberClick(5)} className="btn-number">5</button>
                  <button onClick={() => handleNumberClick(6)} className="btn-number">6</button>
                  <button onClick={() => handleOperatorClick('-')} className="btn-operator">-</button>
                </div>

                <div className="grid grid-cols-4 gap-2">
                  <button onClick={() => handleNumberClick(1)} className="btn-number">1</button>
                  <button onClick={() => handleNumberClick(2)} className="btn-number">2</button>
                  <button onClick={() => handleNumberClick(3)} className="btn-number">3</button>
                  <button onClick={() => handleOperatorClick('+')} className="btn-operator">+</button>
                </div>

                <div className="grid grid-cols-4 gap-2">
                  <button onClick={() => handleNumberClick(0)} className="btn-number">0</button>
                  <button onClick={() => handleNumberClick('.')} className="btn-number">.</button>
                  <button onClick={() => handleOperatorClick('^')} className="btn-operator">xʸ</button>
                  <button onClick={calculate} className="btn-equals">=</button>
                </div>

                {/* 内存和模式 */}
                <div className="grid grid-cols-5 gap-2">
                  <button onClick={memoryClear} className="btn-memory">MC</button>
                  <button onClick={memoryRecall} className="btn-memory">MR</button>
                  <button onClick={memoryAdd} className="btn-memory">M+</button>
                  <button onClick={() => handleOperatorClick('(')} className="btn-function">(</button>
                  <button onClick={() => handleOperatorClick(')')} className="btn-function">)</button>
                </div>

                <div className="grid grid-cols-3 gap-2">
                  <button onClick={() => setAngleMode(angleMode === 'deg' ? 'rad' : 'deg')} 
                          className="btn-special">
                    {angleMode === 'deg' ? 'DEG→RAD' : 'RAD→DEG'}
                  </button>
                  <button onClick={() => setDisplay(display + 'pi')} className="btn-function">π</button>
                  <button onClick={() => setDisplay(display + 'e')} className="btn-function">e</button>
                </div>
              </div>
            )}

            {/* 矩阵模式 */}
            {mode === 'matrix' && (
              <div className="space-y-4">
                <div className="bg-gray-800 rounded-lg p-4">
                  <h3 className="text-white font-bold mb-3">矩阵 A</h3>
                  <div className="grid grid-cols-2 gap-2">
                    {matrixA.map((row, i) => row.map((val, j) => (
                      <input
                        key={`a-${i}-${j}`}
                        type="number"
                        value={val}
                        onChange={(e) => updateMatrix(matrixA, i, j, e.target.value, setMatrixA)}
                        className="bg-gray-700 text-white p-2 rounded text-center"
                      />
                    )))}
                  </div>
                </div>

                <div className="bg-gray-800 rounded-lg p-4">
                  <h3 className="text-white font-bold mb-3">矩阵 B</h3>
                  <div className="grid grid-cols-2 gap-2">
                    {matrixB.map((row, i) => row.map((val, j) => (
                      <input
                        key={`b-${i}-${j}`}
                        type="number"
                        value={val}
                        onChange={(e) => updateMatrix(matrixB, i, j, e.target.value, setMatrixB)}
                        className="bg-gray-700 text-white p-2 rounded text-center"
                      />
                    )))}
                  </div>
                </div>

                <div className="grid grid-cols-3 gap-2">
                  <button onClick={() => matrixOperation('add')} className="btn-operator">A + B</button>
                  <button onClick={() => matrixOperation('subtract')} className="btn-operator">A - B</button>
                  <button onClick={() => matrixOperation('multiply')} className="btn-operator">A × B</button>
                  <button onClick={() => matrixOperation('det')} className="btn-function">det(A)</button>
                  <button onClick={() => matrixOperation('inv')} className="btn-function">A⁻¹</button>
                  <button onClick={() => matrixOperation('transpose')} className="btn-function">Aᵀ</button>
                </div>
              </div>
            )}

            {/* 方程模式 */}
            {mode === 'equation' && (
              <div className="space-y-4">
                <div className="bg-gray-800 rounded-lg p-4">
                  <h3 className="text-white font-bold mb-3">输入方程</h3>
                  <input
                    type="text"
                    value={equation}
                    onChange={(e) => setEquation(e.target.value)}
                    placeholder="例: 2*x + 3 = 7"
                    className="w-full bg-gray-700 text-white p-3 rounded"
                  />
                  <p className="text-gray-400 text-xs mt-2">
                    支持格式: ax + b = c 或输入表达式求值
                  </p>
                </div>
                <button onClick={solveEquation} className="btn-equals w-full">求解</button>
              </div>
            )}

            {/* 绘图模式 */}
            {mode === 'graph' && (
              <div className="space-y-4">
                <div className="bg-gray-800 rounded-lg p-4">
                  <h3 className="text-white font-bold mb-3">函数表达式</h3>
                  <input
                    type="text"
                    value={graphFunc}
                    onChange={(e) => setGraphFunc(e.target.value)}
                    placeholder="例: sin(x), x^2, exp(x)"
                    className="w-full bg-gray-700 text-white p-3 rounded"
                  />
                </div>
                <button onClick={plotGraph} className="btn-special w-full">绘制图形</button>
                
                {graphData.length > 0 && (
                  <div className="bg-white rounded-lg p-4">
                    <ResponsiveContainer width="100%" height={300}>
                      <LineChart data={graphData}>
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="x" />
                        <YAxis />
                        <Tooltip />
                        <Line type="monotone" dataKey="y" stroke="#2563eb" dot={false} />
                      </LineChart>
                    </ResponsiveContainer>
                  </div>
                )}

                <div className="grid grid-cols-3 gap-2">
                  <button onClick={() => setGraphFunc('sin(x)')} className="btn-function">sin(x)</button>
                  <button onClick={() => setGraphFunc('cos(x)')} className="btn-function">cos(x)</button>
                  <button onClick={() => setGraphFunc('tan(x)')} className="btn-function">tan(x)</button>
                  <button onClick={() => setGraphFunc('x^2')} className="btn-function">x²</button>
                  <button onClick={() => setGraphFunc('x^3')} className="btn-function">x³</button>
                  <button onClick={() => setGraphFunc('sqrt(x)')} className="btn-function">√x</button>
                  <button onClick={() => setGraphFunc('exp(x)')} className="btn-function">eˣ</button>
                  <button onClick={() => setGraphFunc('log(x)')} className="btn-function">log(x)</button>
                  <button onClick={() => setGraphFunc('abs(x)')} className="btn-function">|x|</button>
                </div>
              </div>
            )}
          </div>

          {/* 历史记录 */}
          <div className="w-64 bg-gray-800 p-4 border-l border-gray-700">
            <h3 className="text-white font-bold mb-3">计算历史</h3>
            <div className="space-y-2 max-h-96 overflow-y-auto">
              {history.slice().reverse().map((item, idx) => (
                <div key={idx} className="bg-gray-700 rounded p-2 text-sm">
                  <div className="text-gray-300">{item.expr}</div>
                  <div className="text-blue-400 font-bold">= {item.result}</div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

      <style jsx>{`
        .btn-number {
          @apply bg-gray-700 hover:bg-gray-600 text-white font-semibold py-4 px-6 rounded-lg transition-all shadow-md active:scale-95;
        }
        .btn-operator {
          @apply bg-orange-600 hover:bg-orange-500 text-white font-bold py-4 px-6 rounded-lg transition-all shadow-md active:scale-95;
        }
        .btn-function {
          @apply bg-blue-700 hover:bg-blue-600 text-white font-semibold py-3 px-4 rounded-lg transition-all text-sm shadow-md active:scale-95;
        }
        .btn-special {
          @apply bg-red-700 hover:bg-red-600 text-white font-bold py-4 px-6 rounded-lg transition-all shadow-md active:scale-95;
        }
        .btn-equals {
          @apply bg-green-600 hover:bg-green-500 text-white font-bold py-4 px-6 rounded-lg transition-all shadow-md active:scale-95;
        }
        .btn-memory {
          @apply bg-purple-700 hover:bg-purple-600 text-white font-semibold py-3 px-4 rounded-lg transition-all text-sm shadow-md active:scale-95;
        }
      `}</style>
    </div>
  );
};

export default CasioCalculator;